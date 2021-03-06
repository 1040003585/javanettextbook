package _99Socket_Wu;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

public class ServerSocket implements java.io.Closeable {
	/**
	 * Various states of this socket.
	 */
	private boolean created = false;
	private boolean bound = false;
	private boolean closed = false;
	private Object closeLock = new Object();

	/**
	 * The implementation of this Socket.
	 */
	private SocketImpl impl;

	/**
	 * Are we using an older SocketImpl?
	 */
	private boolean oldImpl = false;

	/**
	 * Package-private constructor to create a ServerSocket associated with the
	 * given SocketImpl.
	 */
	ServerSocket(SocketImpl impl) {
		this.impl = impl;
		impl.setServerSocket(this);
	}

	/**
	 * Creates an unbound server socket.
	 * 
	 * @exception IOException
	 *                IO error when opening the socket.
	 * @revised 1.4
	 */
	public ServerSocket() throws IOException {
		setImpl();
	}

	public ServerSocket(int port) throws IOException {
		this(port, 50, null);
	}

	public ServerSocket(int port, int backlog) throws IOException {
		this(port, backlog, null);
	}

	public ServerSocket(int port, int backlog, InetAddress bindAddr)
			throws IOException {
		setImpl();
		if (port < 0 || port > 0xFFFF)
			throw new IllegalArgumentException("Port value out of range: "
					+ port);
		if (backlog < 1)
			backlog = 50;
		try {
			bind(new InetSocketAddress(bindAddr, port), backlog);
		} catch (SecurityException e) {
			close();
			throw e;
		} catch (IOException e) {
			close();
			throw e;
		}
	}

	SocketImpl getImpl() throws SocketException {
		if (!created)
			createImpl();
		return impl;
	}

	private void checkOldImpl() {
		if (impl == null)
			return;
		// SocketImpl.connect() is a protected method, therefore we need to use
		// getDeclaredMethod, therefore we need permission to access the member
		try {
			AccessController
					.doPrivileged(new PrivilegedExceptionAction<Void>() {
						public Void run() throws NoSuchMethodException {
							Class[] cl = new Class[2];
							cl[0] = SocketAddress.class;
							cl[1] = Integer.TYPE;
							impl.getClass().getDeclaredMethod("connect", cl);
							return null;
						}
					});
		} catch (java.security.PrivilegedActionException e) {
			oldImpl = true;
		}
	}

	private void setImpl() {
		if (factory != null) {
			impl = factory.createSocketImpl();
			checkOldImpl();
		} else {
			// No need to do a checkOldImpl() here, we know it's an up to date
			// SocketImpl!
			impl = new SocksSocketImpl();
		}
		if (impl != null)
			impl.setServerSocket(this);
	}


	void createImpl() throws SocketException {
		if (impl == null)
			setImpl();
		try {
			impl.create(true);
			created = true;
		} catch (IOException e) {
			throw new SocketException(e.getMessage());
		}
	}
	public void bind(SocketAddress endpoint) throws IOException {
		bind(endpoint, 50);
	}
	public void bind(SocketAddress endpoint, int backlog) throws IOException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		if (!oldImpl && isBound())
			throw new SocketException("Already bound");
		if (endpoint == null)
			endpoint = new InetSocketAddress(0);
		if (!(endpoint instanceof InetSocketAddress))
			throw new IllegalArgumentException("Unsupported address type");
		InetSocketAddress epoint = (InetSocketAddress) endpoint;
		if (epoint.isUnresolved())
			throw new SocketException("Unresolved address");
		if (backlog < 1)
			backlog = 50;
		try {
			SecurityManager security = System.getSecurityManager();
			if (security != null)
				security.checkListen(epoint.getPort());
			getImpl().bind(epoint.getAddress(), epoint.getPort());
			getImpl().listen(backlog);
			bound = true;
		} catch (SecurityException e) {
			bound = false;
			throw e;
		} catch (IOException e) {
			bound = false;
			throw e;
		}
	}

	public InetAddress getInetAddress() {
		if (!isBound())
			return null;
		try {
			InetAddress in = getImpl().getInetAddress();
			if (!NetUtil.doRevealLocalAddress()) {
				SecurityManager sm = System.getSecurityManager();
				if (sm != null)
					sm.checkConnect(in.getHostAddress(), -1);
			}
			return in;
		} catch (SecurityException e) {
			return InetAddress.getLoopbackAddress();
		} catch (SocketException e) {
			// nothing
			// If we're bound, the impl has been created
			// so we shouldn't get here
		}
		return null;
	}

	public int getLocalPort() {
		if (!isBound())
			return -1;
		try {
			return getImpl().getLocalPort();
		} catch (SocketException e) {
			// nothing
			// If we're bound, the impl has been created
			// so we shouldn't get here
		}
		return -1;
	}


	public SocketAddress getLocalSocketAddress() {
		if (!isBound())
			return null;
		return new InetSocketAddress(getInetAddress(), getLocalPort());
	}

	public Socket accept() throws IOException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		if (!isBound())
			throw new SocketException("Socket is not bound yet");
		Socket s = new Socket((SocketImpl) null);
		implAccept(s);
		return s;
	}

	protected final void implAccept(Socket s) throws IOException {
		SocketImpl si = null;
		try {
			if (s.impl == null)
				s.setImpl();
			else {
				s.impl.reset();
			}
			si = s.impl;
			s.impl = null;
			si.address = new InetAddress();
			si.fd = new FileDescriptor();
			getImpl().accept(si);

			SecurityManager security = System.getSecurityManager();
			if (security != null) {
				security.checkAccept(si.getInetAddress().getHostAddress(), si
						.getPort());
			}
		} catch (IOException e) {
			if (si != null)
				si.reset();
			s.impl = si;
			throw e;
		} catch (SecurityException e) {
			if (si != null)
				si.reset();
			s.impl = si;
			throw e;
		}
		s.impl = si;
		s.postAccept();
	}

	public void close() throws IOException {
		synchronized (closeLock) {
			if (isClosed())
				return;
			if (created)
				impl.close();
			closed = true;
		}
	}

	public ServerSocketChannel getChannel() {
		return null;
	}

	public boolean isBound() {
		// Before 1.3 ServerSockets were always bound during creation
		return bound || oldImpl;
	}

	public boolean isClosed() {
		synchronized (closeLock) {
			return closed;
		}
	}

	public synchronized void setSoTimeout(int timeout) throws SocketException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		getImpl().setOption(SocketOptions.SocketOptions, new Integer(timeout));
	}

	public synchronized int getSoTimeout() throws IOException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		Object o = getImpl().getOption(SocketOptions.SocketOptions);
		/* extra type safety */
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		} else {
			return 0;
		}
	}

	public void setReuseAddress(boolean on) throws SocketException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		getImpl().setOption(SocketOptions.SocketOptions, Boolean.valueOf(on));
	}

	public boolean getReuseAddress() throws SocketException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		return ((Boolean) (getImpl().getOption(SocketOptions.SocketOptions)))
				.booleanValue();
	}

	public String toString() {
		if (!isBound())
			return "ServerSocket[unbound]";
		InetAddress in;
		if (!NetUtil.doRevealLocalAddress()
				&& System.getSecurityManager() != null) {
			in = InetAddress.getLoopbackAddress();
		} else {
			in = impl.getInetAddress();
		}
		return "ServerSocket[addr=" + in + ",localport=" + impl.getLocalPort()
				+ "]";
	}

	void setBound() {
		bound = true;
	}

	void setCreated() {
		created = true;
	}

	private static SocketImplFactory factory = null;

	public static synchronized void setSocketFactory(SocketImplFactory fac)
			throws IOException {
		if (factory != null) {
			throw new SocketException("factory already defined");
		}
		SecurityManager security = System.getSecurityManager();
		if (security != null) {
			security.checkSetFactory();
		}
		factory = fac;
	}

	public synchronized void setReceiveBufferSize(int size)
			throws SocketException {
		if (!(size > 0)) {
			throw new IllegalArgumentException("negative receive size");
		}
		if (isClosed())
			throw new SocketException("Socket is closed");
		getImpl().setOption(SocketOptions.SocketOptions, new Integer(size));
	}

	public synchronized int getReceiveBufferSize() throws SocketException {
		if (isClosed())
			throw new SocketException("Socket is closed");
		int result = 0;
		Object o = getImpl().getOption(SocketOptions.SocketOptions);
		if (o instanceof Integer) {
			result = ((Integer) o).intValue();
		}
		return result;
	}

	public void setPerformancePreferences(int connectionTime, int latency,
			int bandwidth) {
		/* Not implemented yet */
	}

}

import socket

socket.setdefaulttimeout(2)
s=socket.socket()
s.connect(("localhost",9955))
print("C:input data (with 'end' for exit the programing)")
goon=True
while(goon):
	print "C:-------------------------------------"
	print "C:Please input data:"
	indata=input()
	s.send(str(indata)+"\n") #must add "\n"
	data=s.recv(1024).strip('\n')
	if "end"!=data :
		print("C:receive result:"+data)
	else:
		goon=False;
		print("C:end...")
s.close()

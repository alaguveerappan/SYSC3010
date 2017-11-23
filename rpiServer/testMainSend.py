import socket


s = socket.socket()
s.connect(('localhost', 5556))
s.sendall("Test Message")

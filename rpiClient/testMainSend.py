import socket


s = socket.socket()
s.connect(('localhost', 5556))
s.send(b'1')

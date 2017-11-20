import unittest
#from main import main
import socket

class MyTest(unittest.TestCase):
    def test_received_data(self):        
        self.assertEqual(received_data, "1")

sock = socket.socket()
sock.bind(('', 5556))
sock.listen(1)
while True:
    data, addr = sock.accept()
    try:
        received_data = data.recv(1024)
        break
    finally:
        sock.close()

if __name__ == '__main__':
    unittest.main()

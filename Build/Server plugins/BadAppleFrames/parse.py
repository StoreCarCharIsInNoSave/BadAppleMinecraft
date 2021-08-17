import cv2
video = cv2.VideoCapture(f"video.mp4")
countofframes = 0
canread = 1
while canread:
    canread,frame = video.read()
    cv2.imwrite("%d.jpg" % countofframes, frame)
    countofframes+=1

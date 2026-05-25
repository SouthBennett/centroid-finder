# Video Options
## 1. JavaCV

JavaCV is a Java-friendly wrapper that gives developers easier access to tools like OpenCV and FFmpeg. It helps Java programs process video and webcams more easily.

 Pros:
- Easy Setup
- Good at Frame-by-Frame Processing
- Works with Maven

 Cons:
- Frame conversion could be tough to understand
- Complex threading
- There migh be dependency conflicts


## 2. JCodec

JCodec is a pure Java video library. It can decode some video formats and extract frames without needing native FFmpeg installed.

Pros:
- Pure Java, we are used to java more than other libraries and code right now.
- Easier dependency setup.
- Good if we only need simple frame extraction.

Cons:
- Supports fewer video formats.
- May struggle with some modern mp4 videos.
- Documentation/examples can might be limited.

## 3. OpenCV

OpenCV is a computer vision library used for analyzing images and video. It helps programs detect movement, track objects, process frames and work with webcams or cameras

Pros:
- OpenCV is very strong when it comes to motion tracking and image processing.
- There are alot of tutorials and resources online to help learn how to use it.
- It works well with real-time video and webcam projects

Cons:
- OpenCV can be difficult to set up in java
- Most tutorials are written in Python.
- There's a steep learning curve.
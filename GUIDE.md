# Development Guide
---
## Programming / 编程
The main program is written in Java, and the plugins (including most game features) are written in Lua. If you don't know how to use them, feel free to ask or search for tutorials.

此程序主要使用Java编写，插件则主要使用Lua。若您不会使用，可以询问或者寻找教程。

## Workspace / 工作空间
We are sorry for not providing workspace for any IDEs in our repository.

很抱歉我们并不提供任何IDE的工作环境。

## Compiling / 编译
Please install [Gradle](https://gradle.org/) in your PC. Then, go to the directory where [build.gradle](build.gradle) is located,
use your remote to run

	'gradle build'

请在您的机器上安装[Gradle](https://gradle.org/). 之后视您的操作系统，启动终端并在build.gradle目录下执行上述命令。

## Libraries / 库
The followings are the libraries used in our program. For more information, please look forward to [build.gradle](build.gradle).

[Google Guava](https://github.com/google/guava)


[LWJGL](https://www.lwjgl.org) For a full list of implemented LWJGL features, see [here](LWJGL_features.md) ← 参阅此处以获取使用到的LWJGL特性列表。


[NIO(Netty)](http://netty.io)


[JOML](https://github.com/JOML-CI/JOML)


[SteamWorks Java Wrapper](https://github.com/code-disaster/steamworks4j)


[Ini4j](http://ini4j.sourceforge.net/)


[LuaJ](http://www.luaj.org)


以上为程序所使用到的库。更多信息请参阅[build.gradle](build.gradle)。

# Development Guide
---
## Programming / 编程
This program is mainly written in Java and Scala and the plugins are written in Lua. If you don't know how to use them, feel free to ask or search
tutorials.

此程序主要使用Java和Scala编写，插件则主要使用Lua。若您不会使用，可以询问或者寻找教程。

## Workspace / 工作空间
We are sorry for not providing the workspace for any IDEs in our repository.

很抱歉我们并不提供任何IDE的工作环境。

## Compiling / 编译
Please install [Gradle](https://gradle.org/) in your PC. Then, go to the directory where build.gradle is located,
use the cmd/shell to run

	'gradle build'

请在您的机器上安装[Gradle](https://gradle.org/). 之后视您的操作系统，启动终端并在build.gradle目录下
执行上述命令。

## Libraries / 库
The followings are the libraries used in our program. For more information, please look forward to [build.gradle](build.gradle).

[Google Guava - 23.5 (JRE)](https://github.com/google/guava)


[LWJGL - 3.1.5](https://www.lwjgl.org) For full list of used LWJGL features, see [here](LWJGL_features.md) ← 参阅此处以获知使用到的LWJGL特性列表。


[NIO(Netty) - 4.1.17.Final](http://netty.io)


[JOML - 1.9.6](https://github.com/JOML-CI/JOML)


[SteamWorks Java Wrapper - 1.6.2](https://github.com/code-disaster/steamworks4j)


[Ini4j - 0.5.4](http://ini4j.sourceforge.net/)


以上为程序所使用到的库。更多信息请参阅[build.gradle](build.gradle)。

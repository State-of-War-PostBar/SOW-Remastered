# Development Regulations
---
## Codes
All code and text files must encode with **UTF-8** with a line at EOF.

所有的代码及文本文件必须使用**UTF-8**编码，末尾加空行。

_DO NOT_ change the license without permission.

严禁擅自修改许可证。

For 'import static' OpenGL or GLFW who have extraordinary number of constants and methods, please simply do the following:

	'import static org.lwjgl.opengl.GL45.*'
	'import static org.lwjgl.glfw.GLFW.*'

For others, please reduce it as possible.

对于 'import static'的操作，如果是与OpenGL或者GLFW这一类有着超凡的常量和方法的类有关，无论有多少使用，请直接import static其所有内容。

对于非这一类情况的，尽量减少之。

For listing of methods and variables, put in the order of _non-static > static_ and _public > protected > private_, except for class constructors and the logger instance.

对于方法和变量的排序，请按照 _public > protected > private_ 和_非静态 > 静态来排序_。 构造方法和logger的实例除外。

## Git
_DO NOT_ upload binary files unless there's no other way.

除非别无他法，否则请勿上传二进制文件。

Use 'rebase' instead of 'merge' when local commits history is different from remote's.

当本地与远程的分支的commit历史出现差异时，使用 'rebase' 而不是 'merge'.

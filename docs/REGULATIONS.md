# Development Regulations

## Codes / 代码规范

All code and text files must encode in **UTF-8** with a line at EOF.

所有的代码及文本文件必须使用**UTF-8**编码，末尾加空行。

**DO NOT** change the license without permission.

严禁擅自修改许可证。

For *import static* OpenGL or GLFW that have extraordinary number of constants and methods, please simply do the following:

    import static org.lwjgl.opengl.GL45.*
    import static org.lwjgl.glfw.GLFW.*

For others, please import only the parts you used.

对于 *import static* 的操作，如果是与OpenGL或者GLFW这一类有着极端数量的常量和方法的类有关，请直接import static其所有内容。

对于非这一类情况的，尽量减少之。

## Git / Git使用

Use **rebase** instead of **merge** when local commits history is different from remote's.

当本地与远程的分支的commit历史出现差异时，使用 **rebase** 而不是 **merge**.

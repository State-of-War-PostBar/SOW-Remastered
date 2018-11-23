/**************************************************************************************
 *
 * State Of War Remastered:
 * A free software project to reforge an old game.
 * Copyleft (C) 2018 State Of War Baidu Postbar, all rights not reserved.
 *
 * State Of War Remastered is a free software. You can freely do what the fuck you want to
 * under the DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE as published by Sam Hocevar.
 * By the time this line is written, the version of the document is 2, but you may obtain
 * and use any later version of the document released by Sam Hocevar <sam@hocevar.net>.
 *
 * This program is distributed without any warranty, use all features and code at your
 * own risk. Since the project manager (aka Taxerap) sucks at coding and has the fact
 * of being a selfish and arrogant idiot, this program is most likely to be broken.
 *
 * You should have received a copy of the license along with the source code of this
 * program. If not, please forward to http://wtfpl2.com/.
 *
 * Mission is successfully completed.
 *
 **************************************************************************************/

#ifndef SOWR_COMMON_HPP
#define SOWR_COMMON_HPP 23112018L

#ifdef _WIN32 || (defined (__CYGWIN__) && !defined (_WIN32))
    #define SOWR_WINDOWS
#elif defined (__MACH__) && defined (__APPLE__)
    #define SOWR_OSX
    #define SOWR_POSIX
#else
    #define SOWR_NIX
    #define SOWR_POSIX
#endif

#ifdef _DEBUG
    #define SOWR_DEBUG
#endif

#if (-1)>>1 == -1
    #define SOWR_RSHIFT_ARITH
#endif

#ifdef SOWR_WINDOWS
    constexpr const char *LIB_TYPE = ".dll";
#elif defined (SOWR_OSX)
    constexpr const char *LIB_TYPE = ".dylib";
#else
    constexpr const char *LIB_TYPE = ".so";
#endif

constexpr const char *PROGRAM_NAME = "State Of War Remastered";
constexpr const char *VERSION_NAME_MAJOR = "Alpha";
constexpr const char *VERSION_NAME_MINOR = "0.00A";
constexpr const unsigned int BUILD_NUMBER = 1U;

#endif    // !SOWR_COMMON_HPP

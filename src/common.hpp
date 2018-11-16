/**************************************************************************************
 *
 * State Of War Remastered:
 * A free software project attemping to reforge an old game.
 * Copyleft 2018 State Of War Baidu Postbar, all rights not reserved.
 *
 * State Of War Remastered is a free software. You can redistribute it and modify it
 * under GNU lesser General Public License as published by the Free Software Foundation.
 * In the time this line is written, the version of the document is 3, but you may
 * obtain and use any later version of the document released by the GNU group.
 *
 * The program is distributed without any warranty, use all features and code at your
 * own risk. Since the project manager (aka Taxerap) sucks at coding and has the fact
 * of being a selfish and arrogant idiot, this program is most likely to be broken.
 * See GNU lesser Genral Public License for more detail on warranties.
 *
 * You should have received a copy of the license along with source code of this
 * program. If not, please forward to https://www.gnu.org/licenses/#LGPL.
 *
 * Mission has successfully completed.
 *
 **************************************************************************************/

#ifndef SOWR_COMMON_HPP
#define SOWR_COMMON_HPP

#ifdef _WIN32 || defined (__WIN32__) || defined (WIN32)
    #define SOWR_WINDOWS
#elif defined (__MACH__)
    #define SOWR_OSX
    #define SOWR_POSIX
#else
    #define SOWR_NIX
    #define SOWR_POSIX
#endif

#ifdef _DEBUG
    #define SOWR_DEBUG_MODE
#endif

#ifdef SOWR_WINDOWS
    constexpr const char *LIB_TYPE = ".dll";
#elif defined (SOWR_OSX)
    constexpr const char *LIB_TYPE = ".dylib";
#else
    constexpr const char *LIB_TYPE = ".so";
#endif

constexpr const char *PROGRAM_NAME = "State Of War Remastered";
constexpr const char *VERSION_NAME = "Indev";
constexpr const unsigned int BUILD_NUMBER = 1U;

#endif  // SOWR_COMMON_HPP

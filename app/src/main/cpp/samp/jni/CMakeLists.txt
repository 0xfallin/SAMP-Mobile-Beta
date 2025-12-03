cmake_minimum_required(VERSION 3.10)

project(SAMP_Android LANGUAGES C CXX)

# Set Android specific settings
set(CMAKE_SYSTEM_NAME Android)
set(CMAKE_ANDROID_NDK ${ANDROID_NDK})
set(CMAKE_ANDROID_ARCH_ABI armeabi-v7a)       # 32-bit
set(CMAKE_ANDROID_STL_TYPE c++_static)
set(CMAKE_ANDROID_NATIVE_API_LEVEL 21)

# =====================
# Compiler flags
# =====================
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wno-unused-variable -fvisibility=hidden -fpack-struct=1 -fexceptions -O3 -pthread -fPIC")
set(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -Wall -Wno-unused-variable -fvisibility=hidden -fpack-struct=1 -O3 -pthread -fPIC")

# =====================
# Prebuilt static library
# =====================
add_library(libopus STATIC IMPORTED)
set_target_properties(libopus PROPERTIES
    IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/libs/libopus.a
    INTERFACE_INCLUDE_DIRECTORIES ${CMAKE_CURRENT_SOURCE_DIR}/voice/include/audio
)

# =====================
# Collect source files
# =====================
file(GLOB_RECURSE SAMP_SRC
    "${CMAKE_CURRENT_SOURCE_DIR}/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/game/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/game/RW/RenderWare.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/net/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/util/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/gui/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/voice/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/voice/include/util/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/inih/cpp/INIReader.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/inih/ini.c"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/RakNet/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/RakNet/SAMP/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/imgui/*.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/hash/md5.cpp"
    "${CMAKE_CURRENT_SOURCE_DIR}/vendor/base64/*.c"
)

# =====================
# Build shared library
# =====================
add_library(libsamp SHARED ${SAMP_SRC})

# Link with log, m, z, android, jnigraphics, dl, and libopus
target_link_libraries(libsamp
    log
    m
    z
    android
    jnigraphics
    dl
    libopus
)

# Include directories
target_include_directories(libsamp PRIVATE
    ${CMAKE_CURRENT_SOURCE_DIR}
    ${CMAKE_CURRENT_SOURCE_DIR}/game
    ${CMAKE_CURRENT_SOURCE_DIR}/net
    ${CMAKE_CURRENT_SOURCE_DIR}/util
    ${CMAKE_CURRENT_SOURCE_DIR}/gui
    ${CMAKE_CURRENT_SOURCE_DIR}/voice
    ${CMAKE_CURRENT_SOURCE_DIR}/voice/include
    ${CMAKE_CURRENT_SOURCE_DIR}/vendor/inih/cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/vendor/RakNet
    ${CMAKE_CURRENT_SOURCE_DIR}/vendor/imgui
    ${CMAKE_CURRENT_SOURCE_DIR}/vendor/hash
    ${CMAKE_CURRENT_SOURCE_DIR}/vendor/base64
)

# Specify minimum CMake version
cmake_minimum_required(VERSION 3.18.1)

# Set the library name
project(samp_launcher)

# Set C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Path to your SAMP JNI folder
set(SAMP_SRC_DIR ${CMAKE_CURRENT_SOURCE_DIR})

# Add source files
file(GLOB_RECURSE SOURCES
    "${SAMP_SRC_DIR}/*.cpp"
    "${SAMP_SRC_DIR}/*.c"
)

# Add header files (optional)
include_directories(
    ${SAMP_SRC_DIR}
    ${SAMP_SRC_DIR}/vendor
)

# Build shared library
add_library(
    libsamp SHARED
    ${SOURCES}
)

# Link libraries (e.g., log, android, GLES, OpenSL)
find_library(log-lib log)
find_library(android-lib android)
find_library(glesv2-lib GLESv2)
find_library(OpenSL-lib OpenSLES)

target_link_libraries(
    samp_launcher
    ${log-lib}
    ${android-lib}
    ${glesv2-lib}
    ${OpenSL-lib}
)

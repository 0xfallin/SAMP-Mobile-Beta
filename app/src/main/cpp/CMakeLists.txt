# Minimum CMake version
cmake_minimum_required(VERSION 3.10.2)

# Project name
project(SAMPMobileLauncher)

# C++ standard
set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)  # Required for shared library

# Library output folder
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${CMAKE_SOURCE_DIR}/libs)

# Include directories (headers)
include_directories(
    ${CMAKE_SOURCE_DIR}/
    ${CMAKE_SOURCE_DIR}/game
    ${CMAKE_SOURCE_DIR}/net
    ${CMAKE_SOURCE_DIR}/gui
    ${CMAKE_SOURCE_DIR}/vendor
    ${CMAKE_SOURCE_DIR}/vendor/imgui
    ${CMAKE_SOURCE_DIR}/vendor/inih/cpp
)

# Recursively collect all .cpp files in project folders
file(GLOB_RECURSE SOURCE_FILES
    "${CMAKE_SOURCE_DIR}/*.cpp"
)

# Create the shared library for Android
add_library(
    libsamp   # library name
    SHARED
    ${SOURCE_FILES}
)

# Find Android libraries
find_library(log-lib log)
find_library(android-lib android)
find_library(GLESv2-lib GLESv2)

# Link libraries
target_link_libraries(
    samplauncher
    ${log-lib}
    ${android-lib}
    ${GLESv2-lib}
)

# Optional: define macros for build
target_compile_definitions(samplauncher PRIVATE
    BUILD_BETA
    RAKSAMP_CLIENT
    NETCODE_CONNCOOKIELULZ=0x6969
)

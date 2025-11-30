cmake_minimum_required(VERSION 3.10)

# Project
project(native_loader LANGUAGES C CXX)

# Set C standard
set(CMAKE_C_STANDARD 11)
set(CMAKE_C_STANDARD_REQUIRED ON)

# Set C++ standard
set(CMAKE_CXX_STANDARD 11)  # or 17 if needed
set(CMAKE_CXX_STANDARD_REQUIRED ON)

# Add native loader source
add_library(native_loader SHARED
    native_loader.c
)

# Example linking (SA-MP libraries)
find_library(GTASA_LIB GTASA PATHS ${CMAKE_SOURCE_DIR}/../jniLibs/armeabi-v7a)
find_library(SAMP_LIB samp PATHS ${CMAKE_SOURCE_DIR}/../jniLibs/armeabi-v7a)

target_link_libraries(native_loader ${GTASA_LIB} ${SAMP_LIB} log)

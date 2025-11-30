cmake_minimum_required(VERSION 3.6)  # r14b supports older CMake
project(samp)

# Use C++11 instead of C++17 for r14b compatibility
set(CMAKE_CXX_STANDARD 11)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Define Android macros
add_definitions(-DANDROID -D__ANDROID__)

# ---- ABI DETECTION ----
message(WARNING "Building for ABI: ${ANDROID_ABI}")

if("${ANDROID_ABI}" STREQUAL "armeabi-v7a")
    add_definitions(-DVER_x32=true)
elseif("${ANDROID_ABI}" STREQUAL "arm64-v8a")
    add_definitions(-DVER_x32=false)
endif()

# ---- SOURCE FILES ----
file(GLOB_RECURSE ALL_SRC
    "${CMAKE_SOURCE_DIR}/*.cpp"
    "${CMAKE_SOURCE_DIR}/*.c"
    "${CMAKE_SOURCE_DIR}/game/*.cpp"
    "${CMAKE_SOURCE_DIR}/game/*.c"
    "${CMAKE_SOURCE_DIR}/gui/*.cpp"
    "${CMAKE_SOURCE_DIR}/gui/*.c"
    "${CMAKE_SOURCE_DIR}/net/*.cpp"
    "${CMAKE_SOURCE_DIR}/net/*.c"
    "${CMAKE_SOURCE_DIR}/util/*.cpp"
    "${CMAKE_SOURCE_DIR}/util/*.c"
    "${CMAKE_SOURCE_DIR}/vendor/**/*.cpp"
    "${CMAKE_SOURCE_DIR}/vendor/**/*.c"
)

add_library(
    samp
    SHARED
    ${ALL_SRC}
)

# ---- INCLUDE DIRECTORIES ----
target_include_directories(
    samp PRIVATE
    ${CMAKE_SOURCE_DIR}
    ${CMAKE_SOURCE_DIR}/game
    ${CMAKE_SOURCE_DIR}/game/RW
    ${CMAKE_SOURCE_DIR}/gui
    ${CMAKE_SOURCE_DIR}/net
    ${CMAKE_SOURCE_DIR}/util
    ${CMAKE_SOURCE_DIR}/vendor
    ${CMAKE_SOURCE_DIR}/vendor/hash
    ${CMAKE_SOURCE_DIR}/vendor/imgui
    "${CMAKE_SOURCE_DIR}/vendor/imgui vechi"
    ${CMAKE_SOURCE_DIR}/vendor/inih
    ${CMAKE_SOURCE_DIR}/vendor/raknet
    ${CMAKE_SOURCE_DIR}/vendor/raknet/SAMP
    ${CMAKE_SOURCE_DIR}/vendor/SimpleIni
)

# ---- ANDROID LIBRARIES ----
find_library(log-lib log)
find_library(android-lib android)
find_library(glesv2-lib GLESv2)
find_library(egl-lib EGL)

target_link_libraries(
    samp
    ${log-lib}
    ${android-lib}
    ${glesv2-lib}
    ${egl-lib}
    m
)

# ---- OPTIONAL: Disable some warnings in GCC r14b ----
if(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
    target_compile_options(samp PRIVATE
        -Wno-register        # register keyword warning
        -Wno-c++11-compat    # ignore some compatibility warnings
        -Wno-unused-variable
        -Wno-unused-parameter
    )
endif()

cmake_minimum_required(VERSION 3.10.2)
project(native_loader)

add_library(native_loader SHARED src/main/cpp/native_loader.c)

find_library(log-lib log)

target_link_libraries(native_loader ${log-lib} dl)

cmake_minimum_required(VERSION 3.16)
project(polePositionCR C)

set(CMAKE_C_STANDARD 99)

add_executable(polePositionCR main.c auto.cpp auto.h hoyo.cpp hoyo.h vida.cpp vida.h turbo.cpp turbo.h accesorio.cpp accesorio.h listaenlazada.cpp listaenlazada.h Node.cpp Node.h gamecontroller.cpp gamecontroller.h)
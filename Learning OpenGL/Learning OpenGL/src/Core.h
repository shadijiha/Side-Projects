#pragma once

#ifndef _CORE_H
#define _CORE_H

#include <GL/glew.h>
#include <Windows.h>
#include <iostream>

/* ============== ERROR STUFF ============== */
#ifdef _DEBUG
#define WARNING(x) std::cout << "[Warning]: " << x << "\t@" << __FILE__ << " " << __func__ << ":" << __LINE__ <<std::endl;
#else
#define WARNING(x) 
#endif

#define ASSERT(x) if (!(x)) __debugbreak();
#define GLCall(x) GLClearError();\
	x;\
	ASSERT(GLLogCall(#x, __FILE__, __LINE__))

void GLClearError();

bool GLLogCall(const char* function, const char* file, int line);

#endif // _CORE_H

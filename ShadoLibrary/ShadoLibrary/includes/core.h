#pragma once

#include <concepts>

#ifdef __SHADO_API_DLL_EXPORT
#define SHADO_MATH_API __declspec(dllexport)
#elif __SHADO_API_DLL_IMPORT
#define SHADO_MATH_API __declspec(dllimport)
#else
#define SHADO_MATH_API
#endif

// template<typename T>
// concept Incrementable = requires(T a) {
// 	{ a++ }->std::boolean;
// };

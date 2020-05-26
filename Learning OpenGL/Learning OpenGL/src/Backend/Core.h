#pragma once

#ifdef _DEBUG
#define WARNING(X) std::cout << X << std::endl;
#else
#define WARNING(X) 
#endif

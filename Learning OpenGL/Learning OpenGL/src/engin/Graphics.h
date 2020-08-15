#pragma once

/**
 *
 */

#include "../backend/VertexArray.h"
#include "../backend/IndexBuffer.h"
#include "../backend/Shader.h"

namespace ShadoEngine {

    class Graphics
    {
    public:
        Graphics() = default;
        ~Graphics() = default;
        Graphics(const Graphics& g) = delete;
        Graphics(Graphics&& g) = delete;

    	// Graphics Functions    	
        void DrawRect(int x, int y, unsigned width, unsigned height);

        void Draw(const VertexArray& va, const IndexBuffer& ib, const Shader& shader) const;
    	
    };
	
}



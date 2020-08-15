#include "Graphics.h"

#include <iostream>

namespace ShadoEngine {

	void Graphics::DrawRect(int x, int y, unsigned width, unsigned height) {
		std::cout << "Drawing a rectangle...";
	}

	void Graphics::Draw(const VertexArray& va, const IndexBuffer& ib, const Shader& shader) const {
		shader.bind();
		va.bind();
		ib.bind();

		GLCall(glDrawElements(GL_TRIANGLES, ib.getCount(), GL_UNSIGNED_INT, nullptr));
	}

}

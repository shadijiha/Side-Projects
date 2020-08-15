#include "Renderer.h"

namespace ShadoEngine {
	Renderer::Renderer(unsigned width, unsigned height) {

		//drawThread = std::thread(PaintComponent, new Graphics());
	}

	Renderer::Renderer() {
	}

	void Renderer::Start() {
	}

	void Renderer::Submit(const Scene& scene) {
	}

	void Renderer::Submit(const Scene& scene, const int zIndex) {
	}

	void Renderer::Remove(const long sceneID) {
	}

	void Renderer::Remove(const string& sceneName) {
	}

	void Renderer::UpdateComponent() {
	}

	void Renderer::PaintComponent(const Graphics* const g) {
	}

	void Renderer::SortScenes() {
	}

}


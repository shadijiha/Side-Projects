#include "Scene.h"


namespace ShadoEngine {

	Scene::Scene(string name, int z_index) {
		this->name = name;
		this->id = static_cast<long>(rand()) << (sizeof(int) * 8);
		this->zIndex = z_index;
	}

	Scene::Scene(string name)
		: Scene(name, 0)
	{
	}

	Scene::~Scene() {
	}

	int Scene::compareTo(const Scene& o) {
		const int x = zIndex;
		const int y = o.zIndex;
		return (x < y) ? -1 : ((x == y) ? 0 : 1);
	}
}

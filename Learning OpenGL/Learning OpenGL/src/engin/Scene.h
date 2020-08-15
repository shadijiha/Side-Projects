#pragma once
#include <string>

/**
 * 
 */

#include "Graphics.h"
#include "interfaces/Comparable.h"

class Renderer;

namespace ShadoEngine {

	using namespace std;

	class Scene : IComparable<Scene> {
	public:
		Scene(string name, int z_index);
		Scene(string name);
		~Scene();
		Scene(const Scene& s) = delete;
		Scene(Scene&& s) = delete;

		// Initializes the scene and its variables
		virtual void Init(const Renderer& r) = 0;

		// Updates the state of the scene
		virtual void Update(const float dt) = 0;

		// Draws the scene content to the screen
		virtual void Draw(const Graphics* const  g) = 0;

		// Override
		int compareTo(const Scene& o) override;
		
		// Getters
		virtual long GetId() const final { return id; }
		virtual const string& GetName() const final { return name; }
		virtual int GetZIndex() const { return zIndex; }
		
	private:
		
		long id;
		string name;

	protected:
		int zIndex;
		
	};

}

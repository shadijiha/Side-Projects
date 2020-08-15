#pragma once
#include <thread>
#include <vector>

#include "Scene.h"

namespace ShadoEngine {

	 class Renderer final {
		public:
		Renderer(unsigned int width, unsigned int height);
		Renderer();
		Renderer(const Renderer& r) = delete;
		Renderer(Renderer&& r) = delete;

		void PaintComponent(const Graphics* const g);
		void Start();		
		void Submit(const Scene& scene);
		void Submit(const Scene& scene, const int zIndex);
		void Remove(const long sceneID);
		void Remove(const string& sceneName);

	 private:
		 void UpdateComponent();		 
		 void SortScenes();

	private:
		std::vector<Scene> scenes;

		std::thread drawThread;
		std::thread updateThread;

		long startTime = 0;
		long endTime = 0;
		long framerate = 1000 / 60;
		// time the frame began. Edit the second value (60) to change the prefered FPS
		// (i.e. change to 50 for 50 fps)
		long frameStart;
		// number of frames counted this second
		long frameCount = 0;
		// time elapsed during one frame
		long elapsedTime;
		// accumulates elapsed time over multiple frames
		long totalElapsedTime = 0;
		// the actual calculated framerate reporte
		double reportedFramerate = 0;
	};
}



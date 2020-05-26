#include <iostream>
#include <GL/glew.h>
#include <GLFW/glfw3.h>

#include "backend/Renderer.h"
#include "backend/Texture.h"

class Color {
public:
	Color(uint32_t r, uint32_t g, uint32_t b, uint32_t a) {
		validate(r);
		validate(g);
		validate(b);
		validate(a);
		
		m_red = r, m_green = g, m_blue = b, m_alpha = a;
	}

	Color(uint32_t r, uint32_t g, uint32_t b) : Color(r, g, b, 255) {}

	Color(uint32_t gray) : Color(gray, gray, gray, 255)	{}

	unsigned int red() const {
		return m_red;
	}
	unsigned int green() const {
		return m_green;
	}
	unsigned int blue() const {
		return m_blue;
	}
	unsigned int alpha() const {
		return m_alpha;
	}
	
private:
	unsigned int m_red, m_green, m_blue, m_alpha;

	static void validate(unsigned int c) {
		if (c > 255)
			throw std::exception("Illegal Argument Exception! Cannot have a color greater than 255");
	}
};

class Window {
public:
	Window(uint32_t width, uint32_t height, const std::string& title)
		: texture_("resources/Champie_Riven_profileicon.png")
	{
		/* Initialize the library */
		if (!glfwInit())
			throw std::exception("Failed to initialize GLFW!");

		/* Create a windowed mode window and its OpenGL context */
		window = glfwCreateWindow(width, height, title.c_str(), NULL, NULL);
		if (!window)
		{
			glfwTerminate();
			throw std::exception("Failed to create GLFW window!");
		}

		/* Make the window's context current */
		glfwMakeContextCurrent(window);

		if (glewInit() != GLEW_OK)
		{
			std::cout << "Could not establish GLEW!" << std::endl;
		}
	}

	~Window() {
		glfwTerminate();
	}
	
	void run() {
		
		/* Loop until the user closes the window */
		while (!glfwWindowShouldClose(window))
		{
			/* Render here */
			glClear(GL_COLOR_BUFFER_BIT);

			// =============== FOR DEBUG ONLY ==============
			debugDraw();

			/* Swap front and back buffers */
			glfwSwapBuffers(window);

			/* Poll for and process events */
			glfwPollEvents();
		}
	}
	
	std::string getOpenGLVersion() const {
		/* Get OpenGL version */
		std::cout << "OpenGL version: " << glGetString(GL_VERSION) << std::endl;
	}

	
private:
	GLFWwindow* window;
	Renderer renderer;
	Texture texture_;
	
	float positions[8 * 2] = {
	-0.5f, -0.5f, 0.0f, 0.0f,	// 0
	 0.5f, -0.5f, 1.0f, 0.0f,
	 0.5f,  0.5f, 1.0f, 1.0f,
	-0.5f,  0.5f, 0.0f, 1.0f
	};

	unsigned int indices[6] = {
		0, 1, 2,
		2, 3, 0
	};	

	void init() {

		VertexArray va;
		VertexBuffer vb(positions, 4 * 4 * sizeof(float));
		VertexBufferLayout layout;

		IndexBuffer ib(indices, 6);
		Shader shader("resources/shaders/basic.shader");		
		
		layout.push<float>(2);
		layout.push<float>(2);		
		va.addBuffer(vb, layout);		
		shader.bind();

		texture_.bind();
		shader.setUniform1i("u_Texture", 0);
		
		renderer.draw(va, ib, shader);
	}

	void debugDraw() {
		init();		
	}
};

int main()
{
	//Window* window = new Window(1280, 720, "Test");
	//window->run();
	// Define variables here
	// Triangle test;
	// Triangle test2(new float[6]{
	// 	 0.0f, 0.0f,
	// 	 0.5f, 1.0f,
	// 	 1.0f, 0.0f
	// });
	//

	GLFWwindow* window;
	
	{
		/* Initialize the library */
		if (!glfwInit())
			throw std::exception("Failed to initialize GLFW!");

		/* Create a windowed mode window and its OpenGL context */
		window = glfwCreateWindow(1280, 720, "Test", NULL, NULL);
		if (!window)
		{
			glfwTerminate();
			throw std::exception("Failed to create GLFW window!");
		}

		/* Make the window's context current */
		glfwMakeContextCurrent(window);

		if (glewInit() != GLEW_OK)
		{
			std::cout << "Could not establish GLEW!" << std::endl;
		}
		

		float positions[8 * 2] = {
		-0.5f, -0.5f, 0.0f, 0.0f,	// 0
		 0.5f, -0.5f, 1.0f, 0.0f,
		 0.5f,  0.5f, 1.0f, 1.0f,
		-0.5f,  0.5f, 0.0f, 1.0f
		};

		unsigned int indices[6] = {
			0, 1, 2,
			2, 3, 0
		};

		
		VertexArray va;
		VertexBuffer vb(positions, 4 * 4 * sizeof(float));
		VertexBufferLayout layout;

		IndexBuffer ib(indices, 6);
		Shader shader("resources/shaders/basic.shader");

		layout.push<float>(2);
		layout.push<float>(2);
		va.addBuffer(vb, layout);
		shader.bind();

		Texture texture("D:\\Wamp64\\www\\GitHub\\Side-Projects\\Learning OpenGL\\Learning OpenGL\\resources\\Champie_Riven_profileicon.png");
		texture.bind();
		shader.setUniform1i("u_Texture", 0);

		va.unbind();
		vb.unbind();
		ib.unbind();
		shader.unbind();

		Renderer renderer;

		/* Loop until the user closes the window */
		while (!glfwWindowShouldClose(window))
		{
			/* Render here */
			glClear(GL_COLOR_BUFFER_BIT);

			// =============== FOR DEBUG ONLY ==============
			shader.bind();
			renderer.draw(va, ib, shader);
			

			/* Swap front and back buffers */
			glfwSwapBuffers(window);

			/* Poll for and process events */
			glfwPollEvents();
		}
	}

	glfwTerminate();

	return 0;
}

#include "Shader.h"
#include <iostream>
#include <fstream>
#include <sstream>

Shader::Shader(const std::string& filepath) : filepath(filepath), m_RendererID(0) {

	parseShader();	
	m_RendererID = createShader();
}

Shader::~Shader() {
	GLCall(glDeleteProgram(m_RendererID));
}

void Shader::bind() const {
	GLCall(glUseProgram(m_RendererID));
}

void Shader::unbind() const {
	GLCall(glUseProgram(0));
}

void Shader::setUniform1i(const std::string& name, int value) {
	GLCall(glUniform1i(getUniformLocation(name), value));
}

void Shader::setUniform1f(const std::string& name, float value) {
	GLCall(glUniform1f(getUniformLocation(name), value));
}

void Shader::setUniform4f(const std::string& name, float v0, float v1, float v2, float v3) {
	GLCall(glUniform4f(getUniformLocation(name), v0, v1, v2, v3));
}

void Shader::setShaders(const std::string& vertex_shader, const std::string& fragment_shader) {

	vertexShader = vertex_shader;
	fragmentShader = fragment_shader;

	m_RendererID = createShader();	
}

int Shader::getUniformLocation(const std::string& name) {

	if (m_UniformLocationCache.find(name) != m_UniformLocationCache.end())
		return m_UniformLocationCache[name];
	
	GLCall(int location = glGetUniformLocation(m_RendererID, name.c_str()));
	
	if (location == -1)
		WARNING("Location is -1");

	m_UniformLocationCache[name] = location;
	
	return location;
}

bool Shader::parseShader() {
	enum ShaderType { NONE = -1, VERTEX = 0, FRAGMENT = 1 };

	// Load file
	std::ifstream stream(filepath);
	std::string vs = "";
	std::string fs = "";

	unsigned int line_number = 0;
	std::string line;
	std::stringstream ss[2];
	ShaderType mode = NONE;

	// Get data 
	while (std::getline(stream, line)) {

		if (line.find("#shader") != std::string::npos) {
			if (line.find("vertex") != std::string::npos) {
				mode = VERTEX;
			} else if (line.find("fragment") != std::string::npos) {
				mode = FRAGMENT;
			}
		} else
		{
			ss[(int)mode] << line << '\n';
		}

		line_number++;
	}

	this->vertexShader = ss[0].str();
	this->fragmentShader = ss[1].str();

	return true;
}

unsigned int Shader::createShader() {
	GLCall(unsigned int program = glCreateProgram());
	unsigned int vs = compileShader(GL_VERTEX_SHADER, vertexShader);
	unsigned int fs = compileShader(GL_FRAGMENT_SHADER, fragmentShader);

	GLCall(glAttachShader(program, vs));
	GLCall(glAttachShader(program, fs));
	GLCall(glLinkProgram(program));
	GLCall(glValidateProgram(program));

	GLCall(glDeleteShader(vs));
	GLCall(glDeleteShader(fs));

	return program;
}


#pragma once
#include <string>
#include <unordered_map>

#include "../Core.h"


class Shader {
public:
	Shader(const std::string& filepath);
	~Shader();

	void bind() const;
	void unbind() const;

	// Set uniforms
	void setUniform1i(const std::string& name, int value);
	void setUniform1f(const std::string& name, float value);
	void setUniform4f(const std::string& name, float v0, float v1, float v2, float v3);	

protected:
	Shader() = default;

	void setShaders(const std::string& vertex_shader, const std::string& fragment_shader);
	
private:
	unsigned int m_RendererID;
	std::string filepath;
	std::unordered_map<std::string, int> m_UniformLocationCache;

	std::string vertexShader;
	std::string fragmentShader;

	int getUniformLocation(const std::string& name);

	bool parseShader();
	unsigned int compileShader(unsigned int type, const std::string& source) {
		GLCall(unsigned int id = glCreateShader(type));
		const char* src = source.c_str();
		GLCall(glShaderSource(id, 1, &src, nullptr));
		GLCall(glCompileShader(id));

		int result;
		GLCall(glGetShaderiv(id, GL_COMPILE_STATUS, &result));

		if (result == GL_FALSE) {

			int length;
			GLCall(glGetShaderiv(id, GL_INFO_LOG_LENGTH, &length));

			char* message = (char*)alloca(length * sizeof(char));
			GLCall(glGetShaderInfoLog(id, length, &length, message));

			std::cout << "Failt to compile " << (type == GL_VERTEX_SHADER ? "vertex" : "fragment") << "shader!" << std::endl;
			std::cout << message << std::endl;

			GLCall(glDeleteShader(id));
			return 0;
		}

		return id;
	}
	unsigned int createShader();
};

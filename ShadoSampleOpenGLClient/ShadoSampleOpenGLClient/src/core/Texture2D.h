﻿#pragma once
#include <string>
#include <GL/glew.h>

namespace Shado {
	
	class Texture2D {
	public:
		Texture2D(uint32_t width, uint32_t height);
		Texture2D(const std::string& path);
		~Texture2D();

		void setData(void* data, uint32_t size);

		void bind(uint32_t slot = 0) const;
		void unbind() const;

		int getWidth() const { return m_Width; }
		int getHeight() const { return m_Height; }
		uint32_t getRendererID() const { return m_RendererID; }

		bool operator==(const Texture2D& other) const
		{
			return m_RendererID == ((Texture2D&)other).m_RendererID;
		}

	private:
		uint32_t m_RendererID;
		uint32_t m_Width, m_Height;

		unsigned char* m_ImageData;

		GLenum m_InternalFormat;
		GLenum m_DataFormat;

		std::string m_FilePath;
	};
}

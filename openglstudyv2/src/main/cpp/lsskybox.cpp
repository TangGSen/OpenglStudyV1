//#include "lsskybox.h"
//#include "utils.h"
//void SkyBox::Init(const char *imageDir) {
//	mShader = new SShader[6];
//	mVertexBuffer = new VertexBuffer[6];
//	InitFront(imageDir);
//	InitBack(imageDir);
//	InitLeft(imageDir);
//	InitRight(imageDir);
//	InitTop(imageDir);
//	InitBottom(imageDir);
//}
//void SkyBox::InitFront(const char *imageDir) {
//	mShader[0].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "front.bmp");
//	mShader[0].setTexture("U_Texture", temp);
//	mVertexBuffer[0].setSize(4);
//	mVertexBuffer[0].setPosition(0, -0.5f, -0.5f, -0.5f);
//	mVertexBuffer[0].setTexcoord(0, 0.0f,0.0f);
//	mVertexBuffer[0].setPosition(1, 0.5f, -0.5f, -0.5f);
//	mVertexBuffer[0].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[0].setPosition(2, -0.5f, 0.5f, -0.5f);
//	mVertexBuffer[0].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[0].setPosition(3, 0.5f, 0.5f, -0.5f);
//	mVertexBuffer[0].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::InitBack(const char *imageDir) {
//	mShader[1].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "back.bmp");
//	mShader[1].setTexture("U_Texture", temp);
//	mVertexBuffer[1].setSize(4);
//	mVertexBuffer[1].setPosition(0, 0.5f, -0.5f, 0.5f);
//	mVertexBuffer[1].setTexcoord(0, 0.0f, 0.0f);
//	mVertexBuffer[1].setPosition(1, -0.5f, -0.5f, 0.5f);
//	mVertexBuffer[1].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[1].setPosition(2, 0.5f, 0.5f, 0.5f);
//	mVertexBuffer[1].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[1].setPosition(3, -0.5f, 0.5f, 0.5f);
//	mVertexBuffer[1].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::InitLeft(const char *imageDir) {
//	mShader[2].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "left.bmp");
//	mShader[2].setTexture("U_Texture", temp);
//	mVertexBuffer[2].setSize(4);
//	mVertexBuffer[2].setPosition(0, -0.5f, -0.5f, 0.5f);
//	mVertexBuffer[2].setTexcoord(0, 0.0f, 0.0f);
//	mVertexBuffer[2].setPosition(1, -0.5f, -0.5f, -0.5f);
//	mVertexBuffer[2].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[2].setPosition(2, -0.5f, 0.5f, 0.5f);
//	mVertexBuffer[2].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[2].setPosition(3, -0.5f, 0.5f, -0.5f);
//	mVertexBuffer[2].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::InitRight(const char *imageDir) {
//	mShader[3].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "right.bmp");
//	mShader[3].setTexture("U_Texture", temp);
//	mVertexBuffer[3].setSize(4);
//	mVertexBuffer[3].setPosition(0, 0.5f, -0.5f, -0.5f);
//	mVertexBuffer[3].setTexcoord(0, 0.0f, 0.0f);
//	mVertexBuffer[3].setPosition(1, 0.5f, -0.5f, 0.5f);
//	mVertexBuffer[3].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[3].setPosition(2, 0.5f, 0.5f, -0.5f);
//	mVertexBuffer[3].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[3].setPosition(3, 0.5f, 0.5f, 0.5f);
//	mVertexBuffer[3].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::InitTop(const char *imageDir) {
//	mShader[4].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "top.bmp");
//	mShader[4].setTexture("U_Texture", temp);
//	mVertexBuffer[4].setSize(4);
//	mVertexBuffer[4].setPosition(0, -0.5f, 0.5f, -0.5f);
//	mVertexBuffer[4].setTexcoord(0, 0.0f, 0.0f);
//	mVertexBuffer[4].setPosition(1, 0.5f, 0.5f, -0.5f);
//	mVertexBuffer[4].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[4].setPosition(2, -0.5f, 0.5f, 0.5f);
//	mVertexBuffer[4].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[4].setPosition(3, 0.5f, 0.5f, 0.5f);
//	mVertexBuffer[4].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::InitBottom(const char *imageDir) {
//	mShader[5].init("Res/skybox.vs", "Res/skybox.fs");
//	char temp[256];
//	memset(temp, 0, 256);
//	strcpy(temp, imageDir);
//	strcat(temp, "bottom.bmp");
//	mShader[5].setTexture("U_Texture", temp);
//	mVertexBuffer[5].setSize(4);
//	mVertexBuffer[5].setPosition(0, -0.5f, -0.5f, 0.5f);
//	mVertexBuffer[5].setTexcoord(0, 0.0f, 0.0f);
//	mVertexBuffer[5].setPosition(1, 0.5f, -0.5f, 0.5f);
//	mVertexBuffer[5].setTexcoord(1, 1.0f, 0.0f);
//	mVertexBuffer[5].setPosition(2, -0.5f, -0.5f, -0.5f);
//	mVertexBuffer[5].setTexcoord(2, 0.0f, 1.0f);
//	mVertexBuffer[5].setPosition(3, 0.5f, -0.5f, -0.5f);
//	mVertexBuffer[5].setTexcoord(3, 1.0f, 1.0f);
//}
//void SkyBox::Draw(glm::mat4 &V, glm::mat4&P) {
//	glDisable(GL_DEPTH_TEST);
//	for (int i = 0; i < 6; ++i) {
//		mVertexBuffer[i].bind();
//		mShader[i].bind(glm::value_ptr(mModelMatrix), glm::value_ptr(V), glm::value_ptr(P));
//		glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
//		mVertexBuffer[i].unBind();
//	}
//}

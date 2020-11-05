#include "src/Shado.h"
#include "MainScene.h"

int main() {

	using namespace Shado;
	
	auto& app = Application::get();
	app.getWindow().resize(1920, 1080);
	app.getWindow().setResizable(false);

	/** *****************************
	 *  SUBMIT ALL YOUR SCENES HERE *
	 * ******************************
	 */
	app.submit(new MainScene());

	// After run has been called you cannot add Scene
	app.run();

	app.destroy();	
}
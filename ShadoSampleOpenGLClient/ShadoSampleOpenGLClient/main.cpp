#include "src/Shado.h"
#include "ExampleScene.h"

int main() {

	using namespace Shado;
	
	auto& app = Application::get();

	/** *****************************
	 *  SUBMIT ALL YOUR SCENES HERE *
	 * ******************************
	 */
	app.submit(new ExampleScene);

	// After run has been called you cannot add Scene
	app.run();

	app.destroy();	
}
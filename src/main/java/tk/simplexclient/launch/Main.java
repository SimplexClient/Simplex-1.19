package tk.simplexclient.launch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.launchwrapper.Launch;
import tk.simplexclient.utils.Util;

public class Main {

	public static void main(String[] args) {
		List<String> arguments = new ArrayList<>(Arrays.asList(args));

		arguments.add("--tweakClass");
		arguments.add("tk.simplexclient.launch.Tweaker");

		if(Util.isDevEnv()) {
			arguments.add("--version");
			arguments.add("dev");

			arguments.add("--username");
			arguments.add("Player");

			arguments.add("--uuid");
			arguments.add("d4ad7c09-5497-4980-93b8-21d4be84fd08");

			arguments.add("--accessToken");
			arguments.add("0");
		}

		Launch.main(arguments.toArray(String[]::new));
	}

}

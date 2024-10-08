rootProject.name = "CoinCheck"

gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:testClasses"))

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

// Automatically detect modules.
ModuleDetector.modules(rootDir).forEach { module ->
    include(module)
}

private object ModuleDetector {
    fun modules(rootDir: File): List<String> {
        return rootDir.listDirs().flatMap { dir ->
            findModules(parent = "", dir = dir)
        }
    }

    private fun findModules(parent: String, dir: File): List<String> {
        if (dir.isDirectory.not() || dir.isProject()) {
            return emptyList()
        }

        val current: String = parent + ":" + dir.name
        return if (dir.isModule()) {
            println("include '$current'")
            listOf(current)
        } else {
            dir.listDirs().flatMap { subDir ->
                findModules(parent = current, dir = subDir)
            }
        }
    }

    private fun File.isProject(): Boolean {
        if (isDirectory.not()) {
            return false
        }
        return listFiles().orEmpty().any {
            it.isFile && (it.name == "settings.gradle" || it.name == "settings.gradle.kts")
        }
    }

    private fun File.isModule(): Boolean {
        if (isDirectory.not()) {
            return false
        }
        return listFiles().orEmpty().any {
            it.isFile && (it.name == "build.gradle" || it.name == "build.gradle.kts")
        }
    }

    private fun File.listDirs(): List<File> {
        return listFiles().orEmpty().filter { it.isDirectory }
    }
}
include(":core:repository")

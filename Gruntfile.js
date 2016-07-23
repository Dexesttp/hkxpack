module.exports = function(grunt) {

	grunt.initConfig({
		clean: {
			build: ["build"],
			dist: ["dist/*"]
		},
		copy: {
			dist: {
				files: [
					{expand: true, cwd: 'assets/css/', src:["*"], dest: 'dist/css/'},
					{expand: true, cwd: 'assets/js/', src:["*"], dest: 'dist/js/'},
					{expand: true, cwd: 'assets/fonts/', src:["*"], dest: 'dist/fonts/'},
					{expand: true, cwd: 'assets/img/', src:["*"],dest: 'dist/img/'},
				]
			}
		},
		processhtml: {
			build: {
				options: {
					data: {
						date: grunt.template.today("yyyy-mm-dd HH:MM:ss") + ' - DEV build'
					}
				},
				files: {
					'build/main.html': ['src/html/main.html'],
					'build/about.html': ['src/html/about.html'],
					'build/download.html': ['src/html/download.html'],
					'index.html': ['src/html/index.html']
				}
			},
			dist: {
				options: {
					data: {
						date: grunt.template.today("mmm. yyyy")
					}
				},
				files: {
					'build/main.html': ['src/html/main.html'],
					'build/about.html': ['src/html/about.html'],
					'build/download.html': ['src/html/download.html'],
					'dist/index.html': ['src/html/index.html']
				}
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-processhtml');

	grunt.registerTask('default', ['build']);
	grunt.registerTask('build', ['clean:build', 'processhtml:build']);
	grunt.registerTask('dist', ['clean:build', 'clean:dist', 'copy:dist', 'processhtml:dist']);
};
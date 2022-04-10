const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer: {
    https: false,
  },
  // La aplicación no necesariamente se desplegará en el raíz.                                                                                                                                             
  // vue-cli-service, por favor, en el index.html que generes en la build                                                                                                                                  
  // usa rutas relativas al referenciar los recursos, no rutas absolutas                                                                                                                                   
  // desde el raíz.                                                                                                                                                                                        
  publicPath: ''
});

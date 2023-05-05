'use strict'

const { VueLoaderPlugin } = require('vue-loader')
const path = require('path')
const HtmlWebpackPlugin = require('html-webpack-plugin');

const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const CopyPlugin = require("copy-webpack-plugin")
const postcssPresetEnv = require('postcss-preset-env')

module.exports = function (env, argv) {
  let mode = 'production'

  if (env && env.development) {
    mode = 'development'
  }

  let devtool = false

  if (mode === 'development') {
    devtool = 'source-map'
  }

  const webpackConfig = {
    mode: mode,
    stats: {
      hash: true,
      modules: false,
      entrypoints: false
    },
    devServer: {
      static: {
        directory: path.join(__dirname, 'dist'),
      },
      compress: true,
      port: 8080,
      historyApiFallback: true,
      allowedHosts: "all"
    },
    entry: {
      'main': path.join(__dirname, 'resources/scripts/index.js'),
      'maincss': path.join(__dirname, 'resources/styles/main.css'),
    },
    output: {
      clean: true,
      path: path.join(__dirname, 'dist/resources'),

      filename: '[name].js',
      chunkFilename: '[name].[contenthash].js',
      assetModuleFilename: '[name][ext][query]'
    },
    resolve: {
      extensions: ['.js', '.vue'],
      modules: [
        'node_modules'
      ]
    },
    module: {
      rules: [
        {
          test: /\.vue$/,
          use: 'vue-loader'
        }, {
          test: /\.js$/,
          exclude: /node_modules/,
          use: {
            loader: 'babel-loader',
            options: {
              'plugins': [
                ['@babel/plugin-transform-runtime']
              ],
              'presets': [
                ['@babel/preset-env', {
                  'modules': 'commonjs',
                  'targets': {
                    'browsers': ['> 1%', 'last 2 versions', 'not ie <= 8'],
                    'node': 'current'
                  }
                }]
              ]
            }
          }
        },
        {
          test: /\.css?$/,
          use: [
            MiniCssExtractPlugin.loader,
            // importLoaders https://webpack.js.org/loaders/css-loader/#importloaders
            // this attribute is absolutely necessary to enable any previous loaders
            // !! postcss-preset-env would be skipped otherwise !!
            { loader: 'css-loader', options: { importLoaders: 1 } },
            {
              loader: 'postcss-loader',
              options: {
                postcssOptions: {
                  plugins: [postcssPresetEnv({
                    features: {
                      // https://stackoverflow.com/questions/64565180/how-to-prevent-postcss-preset-env-from-removing-css-logical-properties
                      'logical-properties-and-values': false
                    }
                  })],
                },
              },
            },
          ],
        }
      ]
    },
    devtool: devtool,
    plugins: [
      new VueLoaderPlugin(),
      new MiniCssExtractPlugin({
        filename: '[name].css',
        chunkFilename: '[name].[contenthash].css'
      }),
      new HtmlWebpackPlugin({
        template: path.resolve(__dirname, 'index.html'),
        filename: 'index.html',
        inject: true,
      })
      // new CopyPlugin({
      //   patterns: [
      //     { from: 'resources/static', to: 'static' }
      //   ],
      // }),
    ]
  }

  return webpackConfig
}

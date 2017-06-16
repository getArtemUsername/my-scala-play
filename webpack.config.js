/**
 * Created by klevakin on 16.06.17.
 */

var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
	entry : './ui/index.js',
	output : {
		path : __dirname + '/public/compiled',
		filename : 'bundle.js'
	},
	module : {
		loaders : [{
			test : /\.scss$/,
			loader : ExtractTextPlugin.extract({
				fallback : "style-loader",
				use : "css-loader!sass-loader"
			})
		}, {
			test : /\.jsx?$/,
			loader : 'babel-loader',
			include : /ui/,
			query : {
				presets : ['es2015', 'stage-0', 'react']
			}
		}]
	},
	plugins : [new ExtractTextPlugin("styles.css")]
}

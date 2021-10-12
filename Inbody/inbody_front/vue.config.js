
// Vue3 관련 설정 파일
module.exports = {
    devServer: {
        open: true,
        port: 3000,
        proxy: {
            '/main': {
                target: 'http://localhost:8080/',
                changeOrigin: true,
            }
        },
    },
}

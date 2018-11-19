const app = require('express')()
const cluster = require('cluster')

/*
require('./persistence')(app)
require('./model')(app)
require('./boot')(app)
require('./action')(app)
require('./cache')(app)
require('./middleware')(app)

app.use('/api', require('./route')(app))

app.listen(4444, () => {
    console.log(`app is listenning to port 4444`);
})
*/



if(cluster.isMaster) {

    var cpus = require('os').cpus().length
    for(let i = 0; i < cpus; i++){
        const worker = cluster.fork();
        worker.on('exit', (code, signal) => {
            if (signal) {
                console.log(`worker was killed by signal: ${signal}`);
            } else if (code !== 0) {
                console.log(`worker exited with error code: ${code}`);
            } else {
                console.log('worker success!');
            }
        });

        Object.keys(cluster.workers).forEach(function(id) {
            console.log(cluster.workers[id].process.pid);
            cluster.workers[id].on('online', function online(){
              console.log("Worker pid: " + cluster.workers[id].process.pid + " is online");
            });
            
            cluster.workers[id].on('exit', function online(){
                console.log("Worker pid: " + cluster.workers[id].process.pid + " is online");
                
            });

        });
    }

    cluster.on('exit', (old, code, signal) => {
        cluster.fork()
    })

} else {
    require('./persistence')(app)
    require('./model')(app)
    require('./boot')(app)
    require('./action')(app)
    require('./cache')(app)
    require('./middleware')(app)

    app.use('/api', require('./route')(app))

    app.listen(4444, () => {
        console.log(`app is listenning to port 4444`);
    })
}
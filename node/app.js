var app = require('express').createServer()
  , io = require('socket.io').listen(app)
  , redis = require('redis');
var sub = redis.createClient();

sub.subscribe("tags-romney");
sub.subscribe("tags-santorum");
sub.subscribe("tags-gingrich");
sub.subscribe("tags-paul");

sub.subscribe("tweets-romney");
sub.subscribe("tweets-santorum");
sub.subscribe("tweets-gingrich");
sub.subscribe("tweets-paul");

app.listen(8080);

app.get('/', function (req, res) {
  res.sendfile(__dirname + '/index.html');
});

io.sockets.on('connection', function (socket) {
  sub.on("message", function(pattern, key){
    socket.emit(pattern, key);
  });

});

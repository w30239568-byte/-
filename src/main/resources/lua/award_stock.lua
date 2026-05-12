local stock = redis.call('GET', KEYS[1])
if not stock then
    return -1
end
local remain = tonumber(stock)
local decr = tonumber(ARGV[1])
if remain < decr then
    return -1
end
return redis.call('DECRBY', KEYS[1], decr)

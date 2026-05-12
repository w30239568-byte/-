function uuid() {
  const chars = '0123456789abcdef';
  let str = '';
  for (let i = 0; i < 32; i += 1) {
    str += chars[Math.floor(Math.random() * chars.length)];
  }
  return str;
}

function request(options) {
  const app = getApp();
  const { url, method = 'GET', data = {}, write = false } = options;
  const headers = {
    'content-type': 'application/json',
    Token: app.globalData.token || ''
  };
  if (write) {
    headers['Idempotency-Key'] = uuid();
  }

  return new Promise((resolve, reject) => {
    wx.request({
      url: `${app.globalData.baseUrl}${url}`,
      method,
      data,
      header: headers,
      success: (res) => {
        const body = res.data || {};
        if (body.code === 200) {
          resolve(body.data);
        } else {
          wx.showToast({ title: body.msg || '请求失败', icon: 'none' });
          reject(body);
        }
      },
      fail: (err) => {
        wx.showToast({ title: '网络异常', icon: 'none' });
        reject(err);
      }
    });
  });
}

module.exports = {
  request
};

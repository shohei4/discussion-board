
  /**バリデーションmethod */
  const rules = {
  username: (v) => {
    if (!v || v.length < 3 || v.length > 30)
      return "ユーザー名は3〜30文字で入力してください";
  },
  email: (v) => {
    if (!v || !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(v))
      return "正しいメール形式で入力してください";
  },
  password: (v) => {
    if (!v || v.length < 8)
      return "パスワードは8文字以上で入力してください";
  }
};

//項目の確認
/**
 * @param {Object} data - フォームの入力値
 * @param {string[]} fields - チェックする項目名の配列
 * @returns {string[]} エラーメッセージの配列
 */
function validateForm(data, fields) {
  const errors = [];
  fields.forEach(field => {
    const check = rules[field];
    if (check) {
      const error = check(data[field]);
      if (error) errors.push(error);
    }
  });
  return errors;
}

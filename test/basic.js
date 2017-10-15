const expect = require("chai").expect;
const codecheck = require("codecheck");
const app = codecheck.consoleApp(process.env.APP_COMMAND);

describe("CLIアプリケーション", () => {

  it("は実行時に、エラーを発生しない。", () => {
    return app.codecheck().then( result => {
      expect(result.code).to.equal(0,
        　"CLIアプリケーションはステータスコード'0'で終了すべきです。");
    });
  });
});

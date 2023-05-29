package com.example.controller;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.repository.StaffRepository;

/**
 * ログイン操作を管理するコントローラークラス.
 * 
 * @author seiji_kitahara
 *
 */
@Controller
@RequestMapping("/")
public class LoginController {
	
//	@Autowired
//	private StaffRepository staffRepository;
//	@Autowired
//	private PasswordEncoder passwordEncoder;

	/**
	 * ログイン画面に遷移.
	 * 
	 * @param form
	 * @return
	 */
	@GetMapping("")
	public String index(Model model, @RequestParam(required = false) String error) {
		if(error != null) {
			model.addAttribute("errorMessage", "メールアドレスまたはパスワードが正しくありません");
		}
		return "login";
	}
	
//	/**
//	 * ログイン画面の入力値をチェック.
//	 * 
//	 * @param form ログイン画面の入力フォーム
//	 * @param result バリデーションのエラー結果格納先
//	 * @param model リクエストパラメータ
//	 * @return ログイン成功であれば、商品一覧画面。失敗でユーザーが見つからない場合はログイン画面に戻る
//	 */
//	@PostMapping("/login")
//	public String loginCheck(String, BindingResult result, Model model) {
//		
//		Staff staff = staffRepository.findByMailAddress(form);
//		
//		if(!(passwordEncoder.matches(form.getPassword(), staff.getPassword()))) {
//			String notMatch = "登録済みのアカウントが見つかりませんでした";
//			model.addAttribute("notMatch", notMatch);
//			return "login";
//		}
//		return "/item/show-list";
//	}
	
}

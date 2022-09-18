package jp.co.ginga.cui.impl.quiz;

import java.util.ArrayList;
import java.util.List;

import jp.co.ginga.cui.CuiGameApplication;
import jp.co.ginga.util.exception.ApplicationException;
import jp.co.ginga.util.exception.SystemException;
import jp.co.ginga.util.keybord.Keybord;
import jp.co.ginga.util.properties.MessageProperties;

/**
 * CUIクイズゲーム実装クラス
 * 
 * @author yoshi
 *
 */
public class QuizCuiGameApplicationImpl implements CuiGameApplication {

	/**
	 * クイズ問題リスト
	 */
	private List<QuizQuestion> list;

	/**
	 * 正解数
	 */
	private int correctConut;

	/**
	 * クイズゲーム開始処理
	 */
	@Override
	public void action() throws SystemException {

		System.out.println(MessageProperties.getMessage("quiz.msg.start"));

		// クイズリスト作成
		this.createQuizList();

		for (QuizQuestion quiz : list) {

			// クイズ情報表示
			this.viewProblem(quiz);

			// クイズ判定処理
			this.judge(quiz);

		}

		// クイズゲーム結果表示
		this.viewResult();

		System.out.println(MessageProperties.getMessage("quiz.msg.end"));
	}

	/**
	 * クイズリスト作成処理
	 * 
	 * @throws SystemException
	 */
	private void createQuizList() throws SystemException {

		try {
			list = new ArrayList<QuizQuestion>();
			int numberOfQuiz = Integer.parseInt(MessageProperties.getMessage("quiz.number.questions"));

			for (int i = 0; i < numberOfQuiz; i++) {
				list.add(new QuizQuestion(MessageProperties.getMessage("quiz.question.titile" + (i + 1)),
						MessageProperties.getMessage("quiz.question.body" + (i + 1)),
						MessageProperties.getMessage("quiz.question.choice" + (i + 1)),
						Integer.parseInt(MessageProperties.getMessage("quiz.question.correct" + (i + 1)))));
			}
		} catch (NumberFormatException e) {
			throw new SystemException(MessageProperties.getMessage("error.properties.error"));
		}

	}

	/**
	 * クイズ問題表示処理
	 * 
	 * @param quis
	 */
	private void viewProblem(QuizQuestion quiz) {

		System.out.println(quiz.getProblemTitle());
		System.out.println(quiz.getProblemBody());
		System.out.println(quiz.getProblemChoice());

	}

	/**
	 * クイズ判定処理
	 * 
	 * @param quiz
	 * @throws SystemException
	 * 
	 */
	private void judge(QuizQuestion quiz) throws SystemException {

		while (true) {

			try {
				System.out.println(MessageProperties.getMessage("quiz.msg.input"));

				if (quiz.getCorrect() == Keybord.getInt(1, 3)) {
					this.correctConut++;
				}

				break;
			} catch (ApplicationException e) {
				System.out.println(MessageProperties.getMessage("error.outside.range"));
			}

		}
	}

	/**
	 * クイズゲーム結果表示処理
	 * 
	 * @throws SystemException
	 * 
	 */
	private void viewResult() throws SystemException {

		System.out.println(MessageProperties.getMessage("quiz.msg.correct" + this.correctConut));

	}

}

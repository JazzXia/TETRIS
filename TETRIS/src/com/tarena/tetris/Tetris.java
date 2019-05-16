package com.tarena.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JPanel {
	/** 游戏的当前状态: RUNNING PAUSE GAME_OVER */
	private int state;
	public static final int RUNNING = 0;
	public static final int PAUSE = 1;
	public static final int GAME_OVER = 2;
	/** 速度 */
	private int speed;
	/** 难度级别 */
	private int level;
	/** 下落计数器 当 index%speed==0 时候下落一次 */
	private int index;

	public static final int ROWS = 20;
	public static final int COLS = 10;
	/** 墙 */
	private Cell[][] wall = new Cell[ROWS][COLS];
	/** 正在下落的方块 */
	private Tetromino tetromino;
	/** 下一个进场的方块 */
	private Tetromino nextOne;
	/** 销毁行数 */
	private int lines;
	/** 得分 */
	private int score;

	/** 在Tetris类中增加定时器 */
	private Timer timer;

	/** 在Tetris类中添加 背景图片引用 */
	private static BufferedImage background;
	private static BufferedImage gameOver;
	private static BufferedImage pause;
	public static BufferedImage T;
	public static BufferedImage I;
	public static BufferedImage S;
	public static BufferedImage Z;
	public static BufferedImage J;
	public static BufferedImage L;
	public static BufferedImage O;
	static {// 静态代码块
		try {
			// 注意 Tetris类与tetris.png必须在一个
			// package中!
			background = ImageIO.read(Tetris.class.getResource("tetris.png"));
			gameOver = ImageIO.read(Tetris.class.getResource("game-over.png"));
			pause = ImageIO.read(Tetris.class.getResource("pause.png"));
			T = ImageIO.read(Tetris.class.getResource("T.png"));
			S = ImageIO.read(Tetris.class.getResource("S.png"));
			Z = ImageIO.read(Tetris.class.getResource("Z.png"));
			J = ImageIO.read(Tetris.class.getResource("J.png"));
			L = ImageIO.read(Tetris.class.getResource("L.png"));
			I = ImageIO.read(Tetris.class.getResource("I.png"));
			O = ImageIO.read(Tetris.class.getResource("O.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 在Tetris 类中, 重写paint */
	@Override
	public void paint(Graphics g) {
		// 绘制背景图片
		g.drawImage(background, 0, 0, null);
		g.translate(15, 15);
		paintWall(g);
		paintTetromino(g);
		paintNextOne(g);
		paintScore(g);
		paintState(g);// 绘制游戏的状态
	}

	private void paintState(Graphics g) {
		switch (state) {
		case PAUSE:
			g.drawImage(pause, -15, -15, null);
			break;
		case GAME_OVER:
			g.drawImage(gameOver, -15, -15, null);
			break;
		}
	}

	/** 绘制分数 */
	private void paintScore(Graphics g) {
		int x = 292;
		int y = 162;
		g.setColor(new Color(0xffffff));
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 28);
		g.setFont(font);
		g.drawString("SCORE:" + score, x, y);
		y += 56;
		g.drawString("LINES:" + lines, x, y);
		y += 56;
		g.drawString("LEVEL:" + level, x, y);
		x = 290;
		y = 160;
		g.setColor(new Color(0x667799));
		g.drawString("SCORE:" + score, x, y);
		y += 56;
		g.drawString("LINES:" + lines, x, y);
		y += 56;
		g.drawString("LEVEL:" + level, x, y);
	}

	public void paintTetromino(Graphics g) {
		if (tetromino == null) {
			return;
		}
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int x = cell.getCol() * CELL_SIZE;
			int y = cell.getRow() * CELL_SIZE;
			g.drawImage(cell.getImage(), x, y, null);
		}
	}

	public void paintNextOne(Graphics g) {
		if (nextOne == null) {
			return;
		}
		Cell[] cells = nextOne.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int x = (cell.getCol() + 10) * CELL_SIZE;
			int y = (cell.getRow() + 1) * CELL_SIZE;
			g.drawImage(cell.getImage(), x, y, null);
		}
	}

	public static final int CELL_SIZE = 26;

	/** 封装了绘制墙算法 */
	private void paintWall(Graphics g) {
		for (int row = 0; row < wall.length; row++) {
			for (int col = 0; col < wall[row].length; col++) {
				int x = col * CELL_SIZE;
				int y = row * CELL_SIZE;
				// row=0 1 2 ... 19
				// col=0 1 2 ... 9
				// cell 引用墙上的每个格子
				Cell cell = wall[row][col];
				if (cell == null) {
					// g.drawRect(x, y,
					// CELL_SIZE, CELL_SIZE);
				} else {
					g.drawImage(cell.getImage(), x, y, null);
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris");
		Tetris tetris = new Tetris();
		frame.add(tetris);
		frame.setSize(525, 550);
		frame.setAlwaysOnTop(true);// 总在最上
		frame.setUndecorated(true);// 去掉边框
		frame.setLocationRelativeTo(null);
		// Default 默认 Close关闭 Operation操作
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);// 尽快调用 paint()
		tetris.action();// 在main方法中调用action
	}

	/** 在Tetris中添加软件的启动(action)方法 */
	public void action() {
		// score = 2033;
		// wall[1][2] = new Cell(2,4,S);
		// wall[19][4] = new Cell(2,4,T);
		tetromino = Tetromino.randomOne();
		nextOne = Tetromino.randomOne();
		state = RUNNING;
		this.repaint();// 重绘面板,尽快调用paint绘制墙...
		this.addKeyListener(new KeyAdapter() {
			@Override
			// Java 5 提供, 检查后续是否是重写
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch (state) {
				case GAME_OVER:
					processGameoverKey(key);
					break;
				case PAUSE:
					processPauseKey(key);
					break;
				case RUNNING:
					processRunningKey(key);
				}
				repaint();
			}
		});
		this.setFocusable(true);
		this.requestFocus();
		// 在Action 方法中添加,定时计划任务
		timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				speed = 40 - (lines / 100);
				speed = speed < 1 ? 1 : speed;
				level = 41 - speed;
				if (state == RUNNING && index % speed == 0) {
					softDropAction();
				}
				index++;
				repaint();
			}
		}, 10, 10);
	}

	private void processPauseKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_C:
			index = 0;
			state = RUNNING;
			break;
		}
	}

	protected void processRunningKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_RIGHT:
			Tetris.this.moveRightAction();
			break;
		case KeyEvent.VK_LEFT:
			Tetris.this.moveLeftAction();
			break;
		case KeyEvent.VK_DOWN:
			softDropAction();
			break;
		case KeyEvent.VK_SPACE:
			hardDropAction();
			break;
		case KeyEvent.VK_UP:
			rotateRightAction();
			break;
		case KeyEvent.VK_P:
			state = PAUSE;
			break;
		}

	}

	protected void processGameoverKey(int key) {
		switch (key) {
		case KeyEvent.VK_Q:
			System.exit(0);
			break;
		case KeyEvent.VK_S:
			/** 游戏重新开始 */
			this.lines = 0;
			this.score = 0;
			this.wall = new Cell[ROWS][COLS];
			this.tetromino = Tetromino.randomOne();
			this.nextOne = Tetromino.randomOne();
			this.state = RUNNING;
			this.index = 0;
			break;
		}
	}

	/** Tetris 类中添加方法 */
	public void rotateRightAction() {
		// System.out.println(tetromino);
		tetromino.rotateRight();
		if (outOfBounds() || coincide()) {
			tetromino.rotateLeft();
		}
		// System.out.println(tetromino);
	}

	/** Tetris 类中添加方法 */
	public void moveRightAction() {
		// 正在下落的方块右移动
		tetromino.moveRight();
		// 如果(正在下落的方块)超出边界(Bounds)
		if (outOfBounds() || coincide()) {
			// 正在下落的方块左移动
			tetromino.moveLeft();
		}
	}

	public void moveLeftAction() {
		tetromino.moveLeft();
		// coincode: 重合 检查4格方块与墙是否重合
		if (outOfBounds() || coincide()) {
			tetromino.moveRight();
		}
	}

	/** 检查重合 */
	private boolean coincide() {
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}

	/** 检查 (正在下落的方块)是否超出边界 */
	private boolean outOfBounds() {
		// 正在下落的方块的某个格子出界, 就是出界
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
				return true;
			}
		}
		return false;
	}

	/** 硬下落, 一下到底 */
	public void hardDropAction() {
		while (canDrop()) {
			tetromino.softDrop();
		}
		landIntoWall();
		int lines = destroyLines();
		this.lines += lines;
		this.score += scoreTable[lines];
		if (isGameOver()) {
			state = GAME_OVER;
		} else {
			tetromino = nextOne;
			nextOne = Tetromino.randomOne();
		}
	}

	/** 得分表 */
	private int[] scoreTable = { 0, 1, 10, 100, 500 };

	/** Tetris 中添加下落动作 */
	public void softDropAction() {
		// 1 如果能够下落就下落
		// 2 如果不能下落 着陆到墙里面
		// 3 销毁已经满的行
		// 4 如果没有结束, 就产生下一个方块
		if (canDrop()) {
			tetromino.softDrop();
		} else {
			landIntoWall();
			int lines = destroyLines();
			this.lines += lines;
			// lines = 0 1 2 3 4
			// {0,1,10,100,500};
			this.score += scoreTable[lines];
			if (isGameOver()) {
				state = GAME_OVER;
			} else {
				tetromino = nextOne;
				nextOne = Tetromino.randomOne();
			}
		}
	}

	/** 检查当前方块是否能够下落 */
	private boolean canDrop() {
		// 1 方块的某个格子行到达19就不能下落了
		// 2 方块的某个格子对应墙上的下方出现
		// 格子就不能下落了
		Cell[] cells = tetromino.cells;
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			if (row == ROWS - 1) {
				return false;// 不能下落了
			}
		}
		for (int i = 0; i < cells.length; i++) {
			Cell cell = cells[i];
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row + 1][col] != null) {
				return false;
			}
		}
		return true;
	}

	/** 着陆到墙里面 */
	private void landIntoWall() {
		// 根据每个格子的位置, 进入到墙上对于的位置
		Cell[] cells = tetromino.cells;
		// 增强版for循环, Java 5 提供,编译器处理
		// (本质上是"标准数组迭代"的简化版)
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			wall[row][col] = cell;
		}
	}

	/** 销毁已经满的行, 返回销毁行数 */
	private int destroyLines() {
		// 从0 ~ 19 逐行查找, 如果找到满行, 就
		// 删除这行
		int lines = 0;
		for (int row = 0; row < ROWS; row++) {
			if (isFullCells(row)) {
				deleteRow(row);
				lines++;
			}
		}
		return lines;
	}

	/** 检查row这行是否都是格子 */
	private boolean isFullCells(int row) {
		Cell[] line = wall[row];
		for (Cell cell : line) {
			if (cell == null) {
				return false;
			}
		}
		return true;
	}

	/** 删除一行, row是行号 */
	private void deleteRow(int row) {
		for (int i = row; i >= 1; i--) {
			// 复制: wall[i-1] -> wall[i]
			System.arraycopy(wall[i - 1], 0, wall[i], 0, COLS);
		}
		Arrays.fill(wall[0], null);// fill填充
	}

	/** 检查游戏是否结束 */
	private boolean isGameOver() {
		// 如果下一个方块没有出场位置了, 则游戏结束
		// 就是: 下一个出场方块每个格子行列对应的
		// 墙上位置如果有格子, 就游戏结束
		Cell[] cells = nextOne.cells;
		for (Cell cell : cells) {
			int row = cell.getRow();
			int col = cell.getCol();
			if (wall[row][col] != null) {
				return true;
			}
		}
		return false;
	}
}

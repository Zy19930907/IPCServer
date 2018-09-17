package com.fmkj.Begin;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jvnet.substance.SubstanceLookAndFeel;

import com.fmkj.boardcast.BoardCastManger;
import com.fmkj.gateways.AliLinker;
import com.fmkj.gateways.GatewayManger;
import com.fmkj.sensers.SenserFactory;
import com.fmkj.tools.BoardCastConfigReader;
import com.fmkj.tools.CmdMaker;
import com.fmkj.tools.DateTool;
import com.fmkj.tools.IpConfigReader;
import com.fmkj.tools.LogRecoder;
import com.fmkj.views.ActerView;
import com.fmkj.views.AddBoardCastView;
import com.fmkj.views.AddGateway;
import com.fmkj.views.AliLinkView;
import com.fmkj.views.DebugView;
import com.fmkj.views.ExcuteView;
import com.fmkj.views.IpdControlView;
import com.fmkj.views.IpdView;
import com.fmkj.views.LogView;
import com.fmkj.views.MainView;
import com.fmkj.views.MusicPlayView;
import com.fmkj.views.PekingPowerView;
import com.fmkj.views.SenserInfoView;
import com.fmkj.views.UploadTimeSetView;

public class Main{
	public static GatewayManger gatewayManger ;
	public static MainView mainView;
	public static AddGateway addGateway;
	public static IpConfigReader ipConfigReader;
	public static DebugView debugView;
	public static BoardCastManger boardCastManger = new BoardCastManger();
	public static AddBoardCastView addBoardCastView;
	public static MusicPlayView musicPlayView;
	public static BoardCastConfigReader boardCastConfigReader = new BoardCastConfigReader();
	public static LogRecoder logRecoder = new LogRecoder();
	public static DateTool datetool = new DateTool();
	public static CmdMaker cmdMaker = new CmdMaker();
	public static IpdControlView ipdControlView;
	public static PekingPowerView pekingPowerView;
	public static SenserInfoView senserInfoView;
	public static SenserFactory senserFactory = new SenserFactory();
	public static ActerView acterView;
	public static IpdView ipdView;
	public static ExcuteView excuteView;
	public static LogView logView;
	public static UploadTimeSetView uploadTimeSetView;
	public static AliLinker aliLinker = null;
	public static AliLinkView aliLinkView;
	public static volatile boolean alilink = false;
	public static volatile long secTick = 0;
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
			SubstanceLookAndFeel.setCurrentTheme("org.jvnet.substance.theme.BusinessSkin");
			SubstanceLookAndFeel.setCurrentWatermark("org.jvnet.substance.watermark.SubstanceBubblesWatermark");
			SubstanceLookAndFeel.setCurrentGradientPainter("org.jvnet.substance.painter.SpecularGradientPainter");
			JFrame.setDefaultLookAndFeelDecorated(true);
			JDialog.setDefaultLookAndFeelDecorated(true);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Thread((new Runnable() {
					@Override
					public void run() {
						mainView = new  MainView();
						addGateway = new AddGateway();
						addBoardCastView = new AddBoardCastView();
						musicPlayView = new MusicPlayView();
						debugView = new DebugView();
						ipdControlView = new IpdControlView();
						pekingPowerView = new PekingPowerView();
						acterView = new ActerView();
						senserInfoView = new SenserInfoView();
						ipdView = new IpdView();
						ipConfigReader = new IpConfigReader();
						gatewayManger = new GatewayManger();
						excuteView = new ExcuteView();
						uploadTimeSetView = new UploadTimeSetView();
						uploadTimeSetView.setVisible(false);
						ipConfigReader.readGatewaysFromFile();
						boardCastConfigReader.readBoardCastsFromFile();
						ipdControlView.setVisible(false);
						pekingPowerView.setVisible(false);
						senserInfoView.setVisible(false);
						acterView.setVisible(false);
						ipdView.setVisible(false);
						logView = new LogView();
						aliLinkView = new AliLinkView();
					}
				})).start();
			}
		});
	}
}

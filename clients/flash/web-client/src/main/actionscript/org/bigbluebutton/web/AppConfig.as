package org.bigbluebutton.web {
	
	import org.bigbluebutton.common.command.CallEnterApiCommand;
	import org.bigbluebutton.common.command.DoNothingCommand;
	import org.bigbluebutton.common.command.GetVideoProfilesCommand;
	import org.bigbluebutton.common.command.HandleEnterApiFailedSignalCommand;
	import org.bigbluebutton.common.command.LoadConfigCommand;
	import org.bigbluebutton.common.command.LoadConfigCommand2;
	import org.bigbluebutton.common.command.LoadLocaleXmlCommand;
	import org.bigbluebutton.common.command.ValidateAuthTokenCommand;
	import org.bigbluebutton.common.model.ConfigModel;
	import org.bigbluebutton.common.model.LocaleModel;
	import org.bigbluebutton.common.model.MeetingModel;
	import org.bigbluebutton.common.model.MyUserModel;
	import org.bigbluebutton.common.model.UsersModel;
	import org.bigbluebutton.common.model.VideoProfileModel;
	import org.bigbluebutton.common.service.BbbAppsConnection;
	import org.bigbluebutton.common.service.ConfigService;
	import org.bigbluebutton.common.service.EnterApiService;
	import org.bigbluebutton.common.service.IConfigService;
	import org.bigbluebutton.common.service.IEnterApiService;
	import org.bigbluebutton.common.service.ILocaleXmlLoaderService;
	import org.bigbluebutton.common.service.IVideoProfileService;
	import org.bigbluebutton.common.service.LocaleXmlLoaderService;
	import org.bigbluebutton.common.service.ServerCallbackHandler;
	import org.bigbluebutton.common.service.VideoProfilesService;
	import org.bigbluebutton.common.signal.ConfigLoadedSignal;
	import org.bigbluebutton.common.signal.ConnectFailedSignal;
	import org.bigbluebutton.common.signal.ConnectSuccessSignal;
	import org.bigbluebutton.common.signal.EnterApiCallFailedSignal;
	import org.bigbluebutton.common.signal.EnterApiCallSuccessSignal;
	import org.bigbluebutton.common.signal.LoadConfigSignal;
	import org.bigbluebutton.common.signal.LoadedAppAndLocaleVersionsSignal;
	import org.bigbluebutton.common.signal.LocaleChangedSignal;
	import org.bigbluebutton.common.signal.LocaleXmlLoadedSignal;
	import org.bigbluebutton.common.signal.VideoProfileLoadedSignal;
	import org.bigbluebutton.common.util.ISessionUtil;
	import org.bigbluebutton.common.util.SessionUtil;
	import org.bigbluebutton.lib.chat.models.ChatMessagesSession;
	import org.bigbluebutton.lib.chat.models.IChatMessagesSession;
	import org.bigbluebutton.lib.chat.services.ChatMessageService;
	import org.bigbluebutton.lib.chat.services.IChatMessageService;
	import org.bigbluebutton.lib.common.models.ISaveData;
	import org.bigbluebutton.lib.common.models.SaveData;
	import org.bigbluebutton.lib.common.services.BaseConnection;
	import org.bigbluebutton.lib.common.services.IBaseConnection;
	import org.bigbluebutton.lib.deskshare.services.DeskshareConnection;
	import org.bigbluebutton.lib.deskshare.services.IDeskshareConnection;
	import org.bigbluebutton.lib.main.commands.ConnectCommand;
	import org.bigbluebutton.lib.main.commands.ConnectSignal;
	import org.bigbluebutton.lib.main.commands.ConnectToBbbAppsCommand;
	import org.bigbluebutton.lib.main.commands.DisconnectUserCommand;
	import org.bigbluebutton.lib.main.commands.DisconnectUserSignal;
	import org.bigbluebutton.lib.main.models.ConferenceParameters;
	import org.bigbluebutton.lib.main.models.IConferenceParameters;
	import org.bigbluebutton.lib.main.models.IUserSession;
	import org.bigbluebutton.lib.main.models.UserSession;
	import org.bigbluebutton.lib.main.services.BigBlueButtonConnection;
	import org.bigbluebutton.lib.main.services.IBigBlueButtonConnection;
	import org.bigbluebutton.lib.main.services.ILoginService;
	import org.bigbluebutton.lib.main.services.LoginService;
	import org.bigbluebutton.lib.presentation.commands.LoadSlideCommand;
	import org.bigbluebutton.lib.presentation.commands.LoadSlideSignal;
	import org.bigbluebutton.lib.presentation.services.IPresentationService;
	import org.bigbluebutton.lib.presentation.services.PresentationService;
	import org.bigbluebutton.lib.user.services.IUsersService;
	import org.bigbluebutton.lib.user.services.UsersService;
	import org.bigbluebutton.lib.video.commands.CameraQualityCommand;
	import org.bigbluebutton.lib.video.commands.CameraQualitySignal;
	import org.bigbluebutton.lib.video.commands.ShareCameraSignal;
	import org.bigbluebutton.lib.video.services.IVideoConnection;
	import org.bigbluebutton.lib.video.services.VideoConnection;
	import org.bigbluebutton.lib.voice.commands.ShareMicrophoneCommand;
	import org.bigbluebutton.lib.voice.commands.ShareMicrophoneSignal;
	import org.bigbluebutton.lib.voice.services.IVoiceConnection;
	import org.bigbluebutton.lib.voice.services.VoiceConnection;
	import org.bigbluebutton.lib.whiteboard.services.IWhiteboardService;
	import org.bigbluebutton.lib.whiteboard.services.WhiteboardService;
	import org.bigbluebutton.web.video.commands.ShareCameraCommandWeb;
	
	import robotlegs.bender.extensions.signalCommandMap.api.ISignalCommandMap;
	import robotlegs.bender.framework.api.IConfig;
	import robotlegs.bender.framework.api.IInjector;
	
	public class AppConfig implements IConfig {
		
		[Inject]
		public var injector:IInjector;
		
		[Inject]
		public var signalCommandMap:ISignalCommandMap;
		
		public function configure():void {
			// Singleton mapping
			injector.map(IUserSession).toSingleton(UserSession);
			injector.map(IConferenceParameters).toSingleton(ConferenceParameters);
			injector.map(IUsersService).toSingleton(UsersService);
			injector.map(IWhiteboardService).toSingleton(WhiteboardService);
			injector.map(IPresentationService).toSingleton(PresentationService);
			injector.map(IDeskshareConnection).toSingleton(DeskshareConnection);
			injector.map(IChatMessageService).toSingleton(ChatMessageService);
			injector.map(IChatMessagesSession).toSingleton(ChatMessagesSession);
			injector.map(ISaveData).toSingleton(SaveData);

      injector.map(IConfigService).toSingleton(ConfigService);
      injector.map(IEnterApiService).toSingleton(EnterApiService);
      injector.map(ISessionUtil).toSingleton(SessionUtil);     
      injector.map(IVideoProfileService).toSingleton(VideoProfilesService);
      injector.map(ILocaleXmlLoaderService).toSingleton(LocaleXmlLoaderService);

      
      // Map models.
      injector.map(LocaleModel).asSingleton();
      injector.map(UsersModel).asSingleton();
      injector.map(MyUserModel).asSingleton();
      injector.map(ConfigModel).asSingleton();
      injector.map(VideoProfileModel).asSingleton();
      injector.map(MeetingModel).asSingleton();
      injector.map(BbbAppsConnection).asSingleton();
      injector.map(ServerCallbackHandler).asSingleton();
      
			// Type mapping
			injector.map(IBaseConnection).toType(BaseConnection);
			injector.map(IVoiceConnection).toType(VoiceConnection);
			injector.map(ILoginService).toType(LoginService);
			injector.map(IBigBlueButtonConnection).toType(BigBlueButtonConnection);
			injector.map(IVideoConnection).toType(VideoConnection);
      
			// Signal to Command mapping
			signalCommandMap.map(ConnectSignal).toCommand(ConnectCommand);
			signalCommandMap.map(ShareMicrophoneSignal).toCommand(ShareMicrophoneCommand);
			signalCommandMap.map(ShareCameraSignal).toCommand(ShareCameraCommandWeb);
			signalCommandMap.map(LoadSlideSignal).toCommand(LoadSlideCommand);
			signalCommandMap.map(CameraQualitySignal).toCommand(CameraQualityCommand);
			signalCommandMap.map(DisconnectUserSignal).toCommand(DisconnectUserCommand);
      
      // Bootstrap sequence.
      signalCommandMap.map(LoadConfigSignal).toCommand(LoadConfigCommand);
      signalCommandMap.map(LoadConfigSignal).toCommand(LoadConfigCommand2);
      signalCommandMap.map(ConfigLoadedSignal).toCommand(GetVideoProfilesCommand);
      signalCommandMap.map(VideoProfileLoadedSignal).toCommand(LoadLocaleXmlCommand);
      signalCommandMap.map(LocaleXmlLoadedSignal).toCommand(CallEnterApiCommand);
      signalCommandMap.map(EnterApiCallSuccessSignal).toCommand(ConnectToBbbAppsCommand);
      signalCommandMap.map(EnterApiCallFailedSignal).toCommand(HandleEnterApiFailedSignalCommand);
      signalCommandMap.map(ConnectSuccessSignal).toCommand(ValidateAuthTokenCommand);
      
      // Map independent notification signals.
      // We need to map some signals that doesn't have a corresponding command in order for them
      // to be injected into other classes. The API needs a command so we wire it to a "do-nothing-command".
      // For example, we want a mediator to subscribe to a signal by injecting the signal to the mediator.
      // We have to map the signal so the injector is able to inject an instance of the Signal to the
      // Mediator.
      // See 
      //  http://knowledge.robotlegs.org/discussions/robotlegs-2/3918-signals-best-practices
      //  http://knowledge.robotlegs.org/discussions/questions/2353-injecting-mediator-into-command-is-this-possible
      signalCommandMap.map(LocaleChangedSignal).toCommand(DoNothingCommand);
      signalCommandMap.map(LoadedAppAndLocaleVersionsSignal).toCommand(DoNothingCommand);
      
      signalCommandMap.map(ConnectFailedSignal).toCommand(DoNothingCommand);
      
      
		}
	}
}

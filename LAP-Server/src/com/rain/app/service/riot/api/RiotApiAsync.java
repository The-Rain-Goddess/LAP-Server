package com.rain.app.service.riot.api;

import java.util.Objects;
import java.util.Set;

import com.rain.app.service.riot.api.endpoints.champion.dto.Champion;
import com.rain.app.service.riot.api.endpoints.champion.dto.ChampionList;
import com.rain.app.service.riot.api.endpoints.champion.methods.GetChampion;
import com.rain.app.service.riot.api.endpoints.champion.methods.GetChampions;
import com.rain.app.service.riot.api.endpoints.champion_mastery.dto.ChampionMastery;
import com.rain.app.service.riot.api.endpoints.champion_mastery.methods.GetChampionMasteriesBySummoner;
import com.rain.app.service.riot.api.endpoints.champion_mastery.methods.GetChampionMasteriesBySummonerByChampion;
import com.rain.app.service.riot.api.endpoints.champion_mastery.methods.GetChampionMasteryScoresBySummoner;
import com.rain.app.service.riot.api.endpoints.constants.constant.ChampData;
import com.rain.app.service.riot.api.endpoints.constants.constant.ChampListData;
import com.rain.app.service.riot.api.endpoints.constants.constant.ItemData;
import com.rain.app.service.riot.api.endpoints.constants.constant.ItemListData;
import com.rain.app.service.riot.api.endpoints.constants.constant.Locale;
import com.rain.app.service.riot.api.endpoints.constants.constant.MasteryData;
import com.rain.app.service.riot.api.endpoints.constants.constant.MasteryListData;
import com.rain.app.service.riot.api.endpoints.constants.constant.RuneData;
import com.rain.app.service.riot.api.endpoints.constants.constant.RuneListData;
import com.rain.app.service.riot.api.endpoints.constants.constant.SpellData;
import com.rain.app.service.riot.api.endpoints.constants.constant.SpellListData;
import com.rain.app.service.riot.api.endpoints.constants.dto.Item;
import com.rain.app.service.riot.api.endpoints.constants.dto.ItemList;
import com.rain.app.service.riot.api.endpoints.constants.dto.LanguageStrings;
import com.rain.app.service.riot.api.endpoints.constants.dto.MapData;
import com.rain.app.service.riot.api.endpoints.constants.dto.Mastery;
import com.rain.app.service.riot.api.endpoints.constants.dto.MasteryList;
import com.rain.app.service.riot.api.endpoints.constants.dto.ProfileIconData;
import com.rain.app.service.riot.api.endpoints.constants.dto.Realm;
import com.rain.app.service.riot.api.endpoints.constants.dto.Rune;
import com.rain.app.service.riot.api.endpoints.constants.dto.RuneList;
import com.rain.app.service.riot.api.endpoints.constants.dto.SummonerSpell;
import com.rain.app.service.riot.api.endpoints.constants.dto.SummonerSpellList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataChampion;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataChampionList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataItem;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataItemList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataLanguageStrings;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataLanguages;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataMaps;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataMastery;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataMasteryList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataProfileIcons;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataRealm;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataRune;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataRuneList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataSummonerSpell;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataSummonerSpellList;
import com.rain.app.service.riot.api.endpoints.constants.methods.GetDataVersions;
import com.rain.app.service.riot.api.endpoints.league.dto.LeagueList;
import com.rain.app.service.riot.api.endpoints.league.methods.GetChallengerLeagueByQueue;
import com.rain.app.service.riot.api.endpoints.league.methods.GetLeagueBySummonerId;
import com.rain.app.service.riot.api.endpoints.league.methods.GetLeaguePositionsBySummonerId;
import com.rain.app.service.riot.api.endpoints.league.methods.GetMasterLeagueByQueue;
import com.rain.app.service.riot.api.endpoints.masteries.dto.MasteryPages;
import com.rain.app.service.riot.api.endpoints.masteries.methods.GetMasteriesBySummoner;
import com.rain.app.service.riot.api.endpoints.match.dto.Match;
import com.rain.app.service.riot.api.endpoints.match.dto.MatchReferenceList;
import com.rain.app.service.riot.api.endpoints.match.methods.GetMatch;
import com.rain.app.service.riot.api.endpoints.match.methods.GetMatchByMatchIdAndTournamentCode;
import com.rain.app.service.riot.api.endpoints.match.methods.GetMatchIdsByTournamentCode;
import com.rain.app.service.riot.api.endpoints.match.methods.GetMatchReferenceListByAccountId;
import com.rain.app.service.riot.api.endpoints.match.methods.GetRecentMatchListByAccountId;
import com.rain.app.service.riot.api.endpoints.runes.dto.RunePages;
import com.rain.app.service.riot.api.endpoints.runes.methods.GetRunesBySummoner;
import com.rain.app.service.riot.api.endpoints.spectator.dto.CurrentGameInfo;
import com.rain.app.service.riot.api.endpoints.spectator.dto.FeaturedGames;
import com.rain.app.service.riot.api.endpoints.spectator.methods.GetActiveGameBySummoner;
import com.rain.app.service.riot.api.endpoints.spectator.methods.GetFeaturedGames;
import com.rain.app.service.riot.api.endpoints.stats.constant.Season;
import com.rain.app.service.riot.api.endpoints.stats.dto.PlayerStatsSummaryList;
import com.rain.app.service.riot.api.endpoints.stats.dto.RankedStats;
import com.rain.app.service.riot.api.endpoints.stats.methods.GetPlayerStatsSummary;
import com.rain.app.service.riot.api.endpoints.stats.methods.GetRankedStats;
import com.rain.app.service.riot.api.endpoints.status.dto.ShardStatus;
import com.rain.app.service.riot.api.endpoints.status.methods.GetShardData;
import com.rain.app.service.riot.api.endpoints.summoner.dto.Summoner;
import com.rain.app.service.riot.api.endpoints.summoner.methods.GetSummoner;
import com.rain.app.service.riot.api.endpoints.summoner.methods.GetSummonerByAccount;
import com.rain.app.service.riot.api.endpoints.summoner.methods.GetSummonerByName;
import com.rain.app.service.riot.api.endpoints.tournament.constant.PickType;
import com.rain.app.service.riot.api.endpoints.tournament.constant.SpectatorType;
import com.rain.app.service.riot.api.endpoints.tournament.constant.TournamentMap;
import com.rain.app.service.riot.api.endpoints.tournament.dto.LobbyEventWrapper;
import com.rain.app.service.riot.api.endpoints.tournament.dto.TournamentCode;
import com.rain.app.service.riot.api.endpoints.tournament.methods.CreateTournament;
import com.rain.app.service.riot.api.endpoints.tournament.methods.CreateTournamentCodes;
import com.rain.app.service.riot.api.endpoints.tournament.methods.CreateTournamentProvider;
import com.rain.app.service.riot.api.endpoints.tournament.methods.GetLobbyEventsByCode;
import com.rain.app.service.riot.api.endpoints.tournament.methods.GetTournamentCode;
import com.rain.app.service.riot.api.endpoints.tournament.methods.UpdateTournamentCode;
import com.rain.app.service.riot.api.request.AsyncRequest;
import com.rain.app.service.riot.api.request.RequestListener;
import com.rain.app.service.riot.constant.Platform;
import com.rain.app.service.riot.constant.Region;

public class RiotApiAsync {
	
	private final ApiConfig config;
	private final EndpointManager endpointManager;

	/**
	 * Constructs a RiotApiAsync object. This cannot be done directly. To get an RiotApiAsync object, call {@link RiotApi#getAsyncApi()} on
	 * your {@link RiotApi} object.
	 *
	 * @param config
	 *            ApiConfig object
	 * @param endpointManager
	 *            EndpointManager object
	 */
	RiotApiAsync(ApiConfig config, EndpointManager endpointManager) {
		this.config = config;
		this.endpointManager = endpointManager;
	}

	/**
	 * Adds one or more {@link RequestListener} to get informed when asynchronous requests finish.
	 * 
	 * <p>
	 * Please note, that by adding listeners here, you will listen to ALL asynchronous requests made with this RiotApiAsync object. To only
	 * listen to a specific asynchronous request, you can set listeners directly in the {@link AsyncRequest} using
	 * {@link AsyncRequest#addListeners(RequestListener...)}.
	 * </p>
	 * 
	 * @param listeners
	 *            One or more objects that implement {@link RequestListener}
	 * @see RequestListener
	 */
	public void addListeners(RequestListener... listeners) {
		endpointManager.addListeners(listeners);
	}

	/**
	 * Waits indefinitely until all currently running and queued requests complete.
	 * <p>
	 * If the thread is interrupted while waiting for the requests to complete, this method will throw an {@code InterruptedException} and
	 * the thread's interrupt flag will be cleared.
	 * </p>
	 * <p>
	 * <i>Please note that this method is blocking and thus negates the advantage of the asynchronous nature of this class. Consider using a
	 * {@link RequestListener} instead.</i>
	 * </p>
	 * 
	 * @throws InterruptedException
	 *             If the method is interrupted by calling {@link Thread#interrupt()}. The interrupt flag will be cleared
	 */
	public void awaitAll() throws InterruptedException {
		endpointManager.awaitAll();
	}

	/**
	 * Call a custom {@code ApiMethod}
	 *
	 * @param method
	 *            Custom {@code ApiMethod} object
	 * @return Result Dto (if any)
	 * @throws NullPointerException
	 *             If {@code method} is {@code null}
	 * @throws RiotApiException
	 *             If the API returns an error or unparsable result
	 */
	public AsyncRequest callCustomApiMethod(ApiMethod method) throws RiotApiException {
		Objects.requireNonNull(method);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Creates a tournament and returns its ID.
	 *
	 * @param tournamentName
	 *            The optional name of the tournament.
	 * @param providerId
	 *            The provider ID to specify the regional registered provider data to associate this tournament.
	 * @return A tournament ID
	 * @version 3
	 */
	public AsyncRequest createTournament(String tournamentName, int providerId) {
		ApiMethod method = new CreateTournament(getConfig(), tournamentName, providerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Creates a tournament and returns its ID.
	 *
	 * @param providerId
	 *            The provider ID to specify the regional registered provider data to associate this tournament.
	 * @return A tournament Id
	 * @version 3
	 */
	public AsyncRequest createTournament(int providerId) {
		return createTournament(null, providerId);
	}

	/**
	 * Create tournament codes for the given tournament.
	 *
	 * @param tournamentId
	 *            The tournament ID
	 * @param count
	 *            The number of codes to create (max 1000)
	 * @param teamSize
	 *            The team size of the game. Valid values are 1-5.
	 * @param mapType
	 *            The map type of the game.
	 * @param pickType
	 *            The pick type of the game.
	 * @param spectatorType
	 *            The spectator type of the game.
	 * @param metaData
	 *            Optional string that may contain any data in any format, if specified at all. Used to denote any custom information about
	 *            the game.
	 * @param allowedSummonerIds
	 *            Optional list of participants in order to validate the players eligible to join the lobby.
	 * @return A list of tournament codes
	 * @throws NullPointerException
	 *             If {@code mapType} or {@code pickType} or {@code spectatorType} is {@code null}
	 * @version 3
	 */
	public AsyncRequest createTournamentCodes(int tournamentId, int count, int teamSize, TournamentMap mapType, PickType pickType, SpectatorType spectatorType,
			String metaData, long... allowedSummonerIds) throws RiotApiException {
		Objects.requireNonNull(mapType);
		Objects.requireNonNull(pickType);
		Objects.requireNonNull(spectatorType);
		ApiMethod method = new CreateTournamentCodes(getConfig(), tournamentId, count, teamSize, mapType, pickType, spectatorType, metaData,
				allowedSummonerIds);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Create tournament codes for the given tournament.
	 *
	 * @param tournamentId
	 *            The tournament ID
	 * @param count
	 *            The number of codes to create (max 1000)
	 * @param teamSize
	 *            The team size of the game. Valid values are 1-5.
	 * @param mapType
	 *            The map type of the game.
	 * @param pickType
	 *            The pick type of the game.
	 * @param spectatorType
	 *            The spectator type of the game.
	 * @param allowedSummonerIds
	 *            Optional list of participants in order to validate the players eligible to join the lobby.
	 * @return A list of tournament codes
	 * @version 3
	 */
	public AsyncRequest createTournamentCodes(int tournamentId, int count, int teamSize, TournamentMap mapType, PickType pickType, SpectatorType spectatorType,
			long... allowedSummonerIds) throws RiotApiException {
		return createTournamentCodes(tournamentId, count, teamSize, mapType, pickType, spectatorType, null, allowedSummonerIds);
	}

	/**
	 * Creates a tournament provider and returns its ID.
	 *
	 * @param region
	 *            The region in which the provider will be running tournaments.
	 * @param callbackUrl
	 *            The provider's callback URL to which tournament game results in this region should be posted. (http URLs must use port 80,
	 *            https URLs must use port 443).
	 * @return A provider ID
	 * @throws NullPointerException
	 *             If {@code region} or {@code callbackUrl} is {@code null}
	 * @version 3
	 */
	public AsyncRequest createTournamentProvider(String region, String callbackUrl) {
		Objects.requireNonNull(region);
		Objects.requireNonNull(callbackUrl);
		ApiMethod method = new CreateTournamentProvider(getConfig(), region, callbackUrl);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get current game information for the given summoner ID.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            The ID of the summoner.
	 * @return Current game info
	 * @throws NullPointerException
	 *             If {@code platform} or {@code summonerId} is {@code null}
	 * @version 3
	 * @see CurrentGameInfo
	 */
	public AsyncRequest getActiveGameBySummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(summonerId);
		ApiMethod method = new GetActiveGameBySummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get the challenger league for a given {@code queue}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param queueType
	 *            Game queue type.
	 * @return A league list
	 * @throws NullPointerException
	 *             If {@code platform} or {@code queue} is {@code null}
	 * @version 3
	 * @see LeagueList
	 */
	public AsyncRequest getChallengerLeagueByQueue(Platform platform, String queue) {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(queue);
		ApiMethod method = new GetChallengerLeagueByQueue(getConfig(), platform, queue);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve champion by {@code id}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            The ID of the desired champion
	 * @return The champion of the given ID
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Champion
	 */
	public AsyncRequest getChampion(Platform platform, int id) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetChampion(getConfig(), platform, id);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get all champion mastery entries sorted by number of champion points descending
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with the player
	 * @return A list of champion masteries for a given summoner.
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ChampionMastery
	 */
	public AsyncRequest getChampionMasteriesBySummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetChampionMasteriesBySummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get a champion mastery by {@code summonerId} and {@code championId}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with the player
	 * @param championId
	 *            Champion ID to retrieve Champion Mastery for
	 * @return Champion mastery for a given summoner and championId, or {@code null} if given player has no mastery for given champion.
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ChampionMastery
	 */
	public AsyncRequest getChampionMasteriesBySummonerByChampion(Platform platform, long summonerId, int championId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetChampionMasteriesBySummonerByChampion(getConfig(), platform, summonerId, championId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get a player's total champion mastery score, which is sum of individual champion mastery levels
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with the player
	 * @return The total champion mastery score of a given summoner.
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 */
	public AsyncRequest getChampionMasteryScoresBySummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetChampionMasteryScoresBySummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve all champions.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param freeToPlay
	 *            Optional filter param to retrieve only free to play champions.
	 * @return This object contains a collection of champion information.
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ChampionList
	 */
	public AsyncRequest getChampions(Platform platform, boolean freeToPlay) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetChampions(getConfig(), platform, freeToPlay);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve all champions.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return This object contains a collection of champion information.
	 * @version 3
	 * @see ChampionList
	 */
	public AsyncRequest getChampions(Platform platform) {
		return getChampions(platform, false);
	}

	/**
	 * Get the configuration
	 * 
	 * @return {@link ApiConfig} object
	 * @see ApiConfig
	 */
	protected ApiConfig getConfig() {
		return config;
	}

	/**
	 * Retrieves a champion by its {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Champion ID
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param champData
	 *            Tags to return additional data. Only {@code id}, {@code key}, {@code name}, and {@code title} are returned by default if
	 *            this parameter isn't specified. To return all additional data, use {@code ChampData.ALL}.
	 * @return A single champion
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see net.rithms.riot.api.endpoints.static_data.dto.Champion
	 */
	public AsyncRequest getDataChampion(Platform platform, int id, Locale locale, String version, ChampData... champData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataChampion(getConfig(), platform, id, locale, version, champData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves a champion by its {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Champion ID
	 * @return A single champion
	 * @version 3
	 * @see net.rithms.riot.api.endpoints.static_data.dto.Champion
	 */
	public AsyncRequest getDataChampion(Platform platform, int id) {
		return getDataChampion(platform, id, null, null, (ChampData) null);
	}

	/**
	 * Retrieves champion list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param dataById
	 *            If specified as true, the returned data map will use the champions' IDs as the keys. If specified as false, the returned
	 *            data map will use the champions' keys instead.
	 * @param champListData
	 *            Tags to return additional data. Only {@code id}, {@code key}, {@code name}, and {@code title} are returned by default if
	 *            this parameter isn't specified. To return all additional data, use {@code ChampListData.ALL}.
	 * @return A list with champions
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see net.rithms.riot.api.endpoints.static_data.dto.ChampionList
	 */
	public AsyncRequest getDataChampionList(Platform platform, Locale locale, String version, boolean dataById, ChampListData... champListData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataChampionList(getConfig(), platform, locale, version, dataById, champListData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves champion list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with champions
	 * @version 3
	 * @see net.rithms.riot.api.endpoints.static_data.dto.ChampionList
	 */
	public AsyncRequest getDataChampionList(Platform platform) {
		return getDataChampionList(platform, null, null, false, (ChampListData) null);
	}

	/**
	 * Retrieves item by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Item ID
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param itemData
	 *            Tags to return additional data. Only {@code type}, {@code version}, {@code basic}, {@code data}, {@code id}, {@code name},
	 *            {@code plaintext}, {@code group}, and {@code description} are returned by default if this parameter isn't specified. To
	 *            return all additional data, use {@code ItemData.ALL}.
	 * @return A single item
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Item
	 */
	public AsyncRequest getDataItem(Platform platform, int id, Locale locale, String version, ItemData... itemData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataItem(getConfig(), platform, id, locale, version, itemData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves item by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Item ID
	 * @return A single item
	 * @version 3
	 * @see Item
	 */
	public AsyncRequest getDataItem(Platform platform, int id) {
		return getDataItem(platform, id, null, null, (ItemData) null);
	}

	/**
	 * Retrieves item list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param itemListData
	 *            Tags to return additional data. Only {@code type}, {@code version}, {@code basic}, {@code data}, {@code id}, {@code name},
	 *            {@code plaintext}, {@code group}, and {@code description} are returned by default if this parameter isn't specified. To
	 *            return all additional data, use {@code ItemListData.ALL}.
	 * @return A list of items
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ItemList
	 */
	public AsyncRequest getDataItemList(Platform platform, Locale locale, String version, ItemListData... itemListData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataItemList(getConfig(), platform, locale, version, itemListData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves item list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list of items
	 * @version 3
	 * @see ItemList
	 */
	public AsyncRequest getDataItemList(Platform platform) {
		return getDataItemList(platform, null, null, (ItemListData) null);
	}

	/**
	 * Retrieve supported languages data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with languages
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 */
	public AsyncRequest getDataLanguages(Platform platform) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataLanguages(getConfig(), platform);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve language strings data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @return Language strings
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see LanguageStrings
	 */
	public AsyncRequest getDataLanguageStrings(Platform platform, Locale locale, String version) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataLanguageStrings(getConfig(), platform, locale, version);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve language strings data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return Language strings
	 * @version 3
	 * @see LanguageStrings
	 */
	public AsyncRequest getDataLanguageStrings(Platform platform) {
		return getDataLanguageStrings(platform, null, null);
	}

	/**
	 * Retrieves map data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @return A list of game maps
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see MapData
	 */
	public AsyncRequest getDataMaps(Platform platform, Locale locale, String version) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataMaps(getConfig(), platform, locale, version);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves map data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list of game maps
	 * @version 3
	 * @see MapData
	 */
	public AsyncRequest getDataMaps(Platform platform) {
		return getDataMaps(platform, null, null);
	}

	/**
	 * Retrieves mastery item by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Mastery ID
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param masteryData
	 *            Tags to return additional data. Only {@code id}, {@code name}, and {@code description} are returned by default if this
	 *            parameter isn't specified. To return all additional data, use {@code MasteryData.ALL}.
	 * @return A single mastery
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Mastery
	 */
	public AsyncRequest getDataMastery(Platform platform, int id, Locale locale, String version, MasteryData... masteryData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataMastery(getConfig(), platform, id, locale, version, masteryData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves mastery item by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Mastery ID
	 * @return A single mastery
	 * @version 3
	 * @see Mastery
	 */
	public AsyncRequest getDataMastery(Platform platform, int id) {
		return getDataMastery(platform, id, null, null, (MasteryData) null);
	}

	/**
	 * Retrieves mastery list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param masteryListData
	 *            Tags to return additional data. Only {@code type}, {@code version}, {@code data}, {@code id}, {@code name}, and
	 *            {@code description} are returned by default if this parameter isn't specified. To return all additional data, use
	 *            {@code MasteryListData.ALL}.
	 * @return A list with masteries
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see MasteryList
	 */
	public AsyncRequest getDataMasteryList(Platform platform, Locale locale, String version, MasteryListData... masteryListData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataMasteryList(getConfig(), platform, locale, version, masteryListData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves mastery list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with masteries
	 * @version 3
	 * @see MasteryList
	 */
	public AsyncRequest getDataMasteryList(Platform platform) {
		return getDataMasteryList(platform, null, null, (MasteryListData) null);
	}

	/**
	 * Retrieve profile icons.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @return Profile icons
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ProfileIconData
	 */
	public AsyncRequest getDataProfileIcons(Platform platform, Locale locale, String version) throws RiotApiException {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataProfileIcons(getConfig(), platform, locale, version);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve profile icons.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return Profile icons
	 * @version 3
	 * @see ProfileIconData
	 */
	public AsyncRequest getDataProfileIcons(Platform platform) throws RiotApiException {
		return getDataProfileIcons(platform, null, null);
	}

	/**
	 * Retrieve realm data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A single realm
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Realm
	 */
	public AsyncRequest getDataRealm(Platform platform) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataRealm(getConfig(), platform);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves rune by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Rune ID
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param runeData
	 *            Tags to return additional data. Only {@code id}, {@code name}, {@code rune}, and {@code description} are returned by
	 *            default if this parameter isn't specified. To return all additional data, use {@code RuneData.ALL}.
	 * @return A single rune
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Rune
	 */
	public AsyncRequest getDataRune(Platform platform, int id, Locale locale, String version, RuneData... runeData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataRune(getConfig(), platform, id, locale, version, runeData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves rune by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Rune ID
	 * @return A single rune
	 * @version 3
	 * @see Rune
	 */
	public AsyncRequest getDataRune(Platform platform, int id) {
		return getDataRune(platform, id, null, null, (RuneData) null);
	}

	/**
	 * Retrieves rune list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param runeListData
	 *            Tags to return additional data. Only {@code type}, {@code version}, {@code data}, {@code id}, {@code name}, {@code rune},
	 *            and {@code description} are returned by default if this parameter isn't specified. To return all additional data, use
	 *            {@code RuneListData.ALL}.
	 * @return A list with runes
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see RuneList
	 */
	public AsyncRequest getDataRuneList(Platform platform, Locale locale, String version, RuneListData... runeListData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataRuneList(getConfig(), platform, locale, version, runeListData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves rune list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with runes
	 * @version 3
	 * @see RuneList
	 */
	public AsyncRequest getDataRuneList(Platform platform) {
		return getDataRuneList(platform, null, null, (RuneListData) null);
	}

	/**
	 * Retrieves summoner spell by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Summoner spell ID
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param spellData
	 *            Tags to return additional data. Only {@code id}, {@code key}, {@code name}, {@code description}, and {@code summonerLevel}
	 *            are returned by default if this parameter isn't specified. To return all additional data, use {@code SpellData.ALL}.
	 * @return A single summoner spell
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see SummonerSpell
	 */
	public AsyncRequest getDataSummonerSpell(Platform platform, int id, Locale locale, String version, SpellData... spellData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataSummonerSpell(getConfig(), platform, id, locale, version, spellData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves summoner spell by its unique {@code id}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param id
	 *            Summoner spell ID
	 * @return A single summoner spell
	 * @version 3
	 * @see SummonerSpell
	 */
	public AsyncRequest getDataSummonerSpell(Platform platform, int id) {
		return getDataSummonerSpell(platform, id, null, null, (SpellData) null);
	}

	/**
	 * Retrieves summoner spell list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param locale
	 *            Locale code for returned data (e.g., {@code en_US}, {@code es_ES}). If not specified, the default locale for the region is
	 *            used.
	 * @param version
	 *            Data dragon version for returned data. If not specified, the latest version for the region is used. List of valid versions
	 *            can be obtained from the {@link #getDataVersions()} method.
	 * @param dataById
	 *            If specified as true, the returned data map will use the spells' IDs as the keys. If specified as false, the returned data
	 *            map will use the spells' keys instead
	 * @param spellListData
	 *            Tags to return additional data. Only {@code type}, {@code version}, {@code data}, {@code id}, {@code key}, {@code name},
	 *            {@code description}, and {@code summonerLevel} are returned by default if this parameter isn't specified. To return all
	 *            additional data, use {@code SpellListData.ALL}.
	 * @return A list with summoner spells
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see SummonerSpellList
	 */
	public AsyncRequest getDataSummonerSpellList(Platform platform, Locale locale, String version, boolean dataById, SpellListData... spellListData) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataSummonerSpellList(getConfig(), platform, locale, version, dataById, spellListData);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieves summoner spell list.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with summoner spells
	 * @version 3
	 * @see SummonerSpellList
	 */
	public AsyncRequest getDataSummonerSpellList(Platform platform) {
		return getDataSummonerSpellList(platform, null, null, false, (SpellListData) null);
	}

	/**
	 * Retrieve version data.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return A list with versions
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 */
	public AsyncRequest getDataVersions(Platform platform) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetDataVersions(getConfig(), platform);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get list of featured games.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return Featured games
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see FeaturedGames
	 */
	public AsyncRequest getFeaturedGames(Platform platform) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetFeaturedGames(getConfig(), platform);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get leagues in all queues for a given {@code summonerId}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID
	 * @return List of league lists
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see LeagueList
	 */
	public AsyncRequest getLeagueBySummonerId(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetLeagueBySummonerId(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get league positions in all queues for a given {@code summonerId}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID
	 * @return List of league positions
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @throws RiotApiException
	 *             If the API returns an error or unparsable result
	 * @version 3
	 * @see LeagueList
	 */
	public AsyncRequest getLeaguePositionsBySummonerId(Platform platform, long summonerId) throws RiotApiException {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetLeaguePositionsBySummonerId(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Gets a list of lobby events by {@code tournamentCode}
	 * 
	 * @param tournamentCode
	 *            Tournament code used to enter the lobby.
	 * @return Lobby event data
	 * @throws NullPointerException
	 *             If {@code tournamentCode} is {@code null}
	 * @see LobbyEventWrapper
	 */
	public AsyncRequest getLobbyEventsByTournament(String tournamentCode) {
		Objects.requireNonNull(tournamentCode);
		ApiMethod method = new GetLobbyEventsByCode(getConfig(), tournamentCode);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get the master league for a given {@code queue}.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param queueType
	 *            Game queue type.
	 * @return A league list
	 * @throws NullPointerException
	 *             If {@code platform} or {@code queue} is {@code null}
	 * @throws RiotApiException
	 *             If the API returns an error or unparsable result
	 * @version 3
	 * @see LeagueList
	 */
	public AsyncRequest getMasterLeagueByQueue(Platform platform, String queue) throws RiotApiException {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(queue);
		ApiMethod method = new GetMasterLeagueByQueue(getConfig(), platform, queue);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get mastery pages for a given {@code summonerId}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with masteries to retrieve.
	 * @return Mastery pages of the given summoners
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see MasteryPages
	 */
	public AsyncRequest getMasteriesBySummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetMasteriesBySummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get match by match ID.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param matchId
	 *            The ID of the match.
	 * @return A map with match details
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Match
	 */
	public AsyncRequest getMatch(Platform platform, long matchId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetMatch(getConfig(), platform, matchId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve match IDs by {@code tournamentCode}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param tournamentCode
	 *            The code of the tournament.
	 * @return A list of match IDs
	 * @throws NullPointerException
	 *             If {@code platform} or {@code tournamentCode} is {@code null}
	 * @version 3
	 */
	public AsyncRequest getMatchIdsByTournamentCode(Platform platform, String tournamentCode) {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(tournamentCode);
		ApiMethod method = new GetMatchIdsByTournamentCode(getConfig(), platform, tournamentCode);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Retrieve match by {@code matchId} and {@code tournamentCode}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param matchId
	 *            The ID of the match.
	 * @param tournamentCode
	 *            The code of the tournament.
	 * @return A map with match details
	 * @throws NullPointerException
	 *             If {@code platform} or {@code tournamentCode} is {@code null}
	 * @version 3
	 * @see Match
	 */
	public AsyncRequest getMatchByMatchIdAndTournamentCode(Platform platform, long matchId, String tournamentCode) {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(tournamentCode);
		ApiMethod method = new GetMatchByMatchIdAndTournamentCode(getConfig(), platform, matchId, tournamentCode);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get matchlist for given account ID and platform ID.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param accountId
	 *            The account ID of the summoner.
	 * @param champion
	 *            Set of champion IDs for which to filtering matchlist.
	 * @param queue
	 *            Set of queue IDs for which to filtering matchlist.
	 * @param season
	 *            Set of season IDs for which to filtering matchlist.
	 * @param beginTime
	 *            The begin time to use for filtering matchlist specified as epoch milliseconds. Use {@code -1} to not use this parameter.
	 * @param endTime
	 *            The end time to use for filtering matchlist specified as epoch milliseconds. Use {@code -1} to not use this parameter.
	 * @param beginIndex
	 *            The begin index to use for filtering matchlist. Use {@code -1} to not use this parameter.
	 * @param endIndex
	 *            The end index to use for filtering matchlist. Use {@code -1} to not use this parameter.
	 * @return A list with matches
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see MatchReferenceList
	 */
	public AsyncRequest getMatchReferenceListByAccountId(Platform platform, long accountId, Set<Integer> champion, Set<Integer> queue, Set<Integer> season,
			long beginTime, long endTime, int beginIndex, int endIndex) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetMatchReferenceListByAccountId(getConfig(), platform, accountId, champion, queue, season, beginTime, endTime, beginIndex, endIndex);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get matchlist for given account ID and platform ID.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param accountId
	 *            The account ID of the summoner.
	 * @param champion
	 *            Set of champion IDs for which to filtering matchlist.
	 * @param queue
	 *            Set of queue IDs for which to filtering matchlist.
	 * @param season
	 *            Set of season IDs for which to filtering matchlist.
	 * @return A list with matches
	 * @version 3
	 * @see MatchReferenceList
	 */
	public AsyncRequest getMatchReferenceListByAccountId(Platform platform, long accountId, Set<Integer> champion, Set<Integer> queue, Set<Integer> season) {
		return getMatchReferenceListByAccountId(platform, accountId, champion, queue, season, -1, -1, -1, -1);
	}

	/**
	 * Get matchlist for given account ID and platform ID.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param accountId
	 *            The account ID of the summoner.
	 * @return A list with matches
	 * @version 3
	 * @see MatchReferenceList
	 */
	public AsyncRequest getMatchListByAccountId(Platform platform, long accountId) {
		return getMatchReferenceListByAccountId(platform, accountId, null, null, null);
	}

	/**
	 * Get player stats summaries by {@code summonerId}.
	 *
	 * @param region
	 *            Region where to retrieve the data.
	 * @param summonerId
	 *            ID of the summoner for which to retrieve player stats.
	 * @param season
	 *            If specified, stats for the given season are returned. Otherwise, stats for the current season are returned.
	 * @return A summary of player statistics for the given summoner
	 * @throws NullPointerException
	 *             If {@code region} is {@code null}
	 * @version 1.3
	 * @see PlayerStatsSummaryList
	 */
	@Deprecated
	public AsyncRequest getPlayerStatsSummary(Region region, Season season, long summonerId) {
		Objects.requireNonNull(region);
		ApiMethod method = new GetPlayerStatsSummary(getConfig(), region, season, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get player stats summaries by {@code summonerId}.
	 *
	 * @param region
	 *            Region where to retrieve the data.
	 * @param summonerId
	 *            ID of the summoner for which to retrieve player stats.
	 * @return A summary of player statistics for the given summoner
	 * @version 1.3
	 * @see PlayerStatsSummaryList
	 */
	@Deprecated
	public AsyncRequest getPlayerStatsSummary(Region region, long summonerId) {
		return getPlayerStatsSummary(region, null, summonerId);
	}

	/**
	 * Returns the number of elements in the asynchronous request pool.
	 *
	 * @return Number of elements in the asynchronous request pool
	 */
	public int getPoolSize() {
		return endpointManager.getPoolSize();
	}

	/**
	 * Returns the number of elements in the asynchronous request queue.
	 *
	 * @return Number of elements in the asynchronous request queue
	 */
	public int getQueueSize() {
		return endpointManager.getQueueSize();
	}

	/**
	 * Get ranked stats by {@code summonerId}.
	 *
	 * @param region
	 *            Region where to retrieve the data.
	 * @param summonerId
	 *            ID of the summoner for which to retrieve ranked stats.
	 * @param season
	 *            If specified, stats for the given season are returned. Otherwise, stats for the current season are returned.
	 * @return Ranked statistics of the given summoner
	 * @throws NullPointerException
	 *             If {@code region} is {@code null}
	 * @version 1.3
	 * @see RankedStats
	 */
	@Deprecated
	public AsyncRequest getRankedStats(Region region, Season season, long summonerId) {
		Objects.requireNonNull(region);
		ApiMethod method = new GetRankedStats(getConfig(), region, season, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get ranked stats by {@code summonerId}.
	 *
	 * @param region
	 *            Region where to retrieve the data.
	 * @param summonerId
	 *            ID of the summoner for which to retrieve ranked stats.
	 * @param season
	 *            If specified, stats for the given season are returned. Otherwise, stats for the current season are returned.
	 * @return Ranked statistics of the given summoner
	 * @version 1.3
	 * @see RankedStats
	 */
	@Deprecated
	public AsyncRequest getRankedStats(Region region, long summonerId) {
		return getRankedStats(region, null, summonerId);
	}

	/**
	 * Get recent matchlist for given account ID and platform ID.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param accountId
	 *            The account ID of the summoner.
	 * @return A list with matches
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @throws RiotApiException
	 *             If the API returns an error or unparsable result
	 * @version 3
	 * @see MatchReferenceList
	 */
	public AsyncRequest getRecentMatchListByAccountId(Platform platform, long accountId) throws RiotApiException {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetRecentMatchListByAccountId(getConfig(), platform, accountId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get rune pages for a given {@code summonerId}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with runes to retrieve.
	 * @return Rune pages of the given summoners
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see RunePages
	 */
	public AsyncRequest getRunesBySummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetRunesBySummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get shard status. Returns the data available on the status.leagueoflegends.com website for the given region.
	 * 
	 * @param platform
	 *            Platform to execute the method call against.
	 * @return Status for a single shard
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see ShardStatus
	 */
	public AsyncRequest getShardData(Platform platform) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetShardData(getConfig(), platform);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get a summoner object for a given {@code accountId}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Account ID associated with summoner to retrieve.
	 * @return The desired summoner
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Summoner
	 */
	public AsyncRequest getSummonerByAccount(Platform platform, long accountId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetSummonerByAccount(getConfig(), platform, accountId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get a summoner object for a given {@code summonerId}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerId
	 *            Summoner ID associated with summoner to retrieve.
	 * @return The desired summoner
	 * @throws NullPointerException
	 *             If {@code platform} is {@code null}
	 * @version 3
	 * @see Summoner
	 */
	public AsyncRequest getSummoner(Platform platform, long summonerId) {
		Objects.requireNonNull(platform);
		ApiMethod method = new GetSummoner(getConfig(), platform, summonerId);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Get a single summoner object for a given {@code summonerName}.
	 *
	 * @param platform
	 *            Platform to execute the method call against.
	 * @param summonerName
	 *            Summoner name associated with summoner to retrieve.
	 * @return A map of desired summoners
	 * @throws NullPointerException
	 *             If {@code platform} or {@code summonerName} is {@code null}
	 * @version 3
	 * @see Summoner
	 */
	public AsyncRequest getSummonerByName(Platform platform, String summonerName) {
		Objects.requireNonNull(platform);
		Objects.requireNonNull(summonerName);
		ApiMethod method = new GetSummonerByName(getConfig(), platform, summonerName);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Returns the tournament code DTO associated with a {@code tournamentCode}.
	 *
	 * @param tournamentCode
	 *            The tournament code
	 * @return Tournament code DTO
	 * @throws NullPointerException
	 *             If {@code tournamentCode} is {@code null}
	 * @version 3
	 * @see TournamentCode
	 */
	public AsyncRequest getTournamentCode(String tournamentCode) {
		Objects.requireNonNull(tournamentCode);
		ApiMethod method = new GetTournamentCode(getConfig(), tournamentCode);
		return endpointManager.callMethodAsynchronously(method);
	}

	/**
	 * Removes one or more {@link RequestListener} from getting informed when asynchronous requests finish.
	 * 
	 * @param listeners
	 *            One or more objects that implement {@link RequestListener}
	 * @see RequestListener
	 */
	public void removeListeners(RequestListener... listeners) {
		endpointManager.removeListeners(listeners);
	}

	/**
	 * Update the pick type, map, spectator type, or allowed summoners for a code.
	 *
	 * @param tournamentCode
	 *            The tournament code
	 * @param mapType
	 *            The map type of the game.
	 * @param pickType
	 *            The pick type of the game.
	 * @param spectatorType
	 *            The spectator type of the game.
	 * @param allowedSummonerIds
	 *            Optional list of participants in order to validate the players eligible to join the lobby.
	 * @throws NullPointerException
	 *             If {@code tournamentCode} is {@code null}
	 * @version 3
	 */
	public void updateTournamentCode(String tournamentCode, TournamentMap mapType, PickType pickType, SpectatorType spectatorType, long... allowedSummonerIds) {
		Objects.requireNonNull(tournamentCode);
		ApiMethod method = new UpdateTournamentCode(getConfig(), tournamentCode, mapType, pickType, spectatorType, allowedSummonerIds);
		endpointManager.callMethodAsynchronously(method);
	}
}

package tc.oc.pgm.shops.menu;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.bukkit.inventory.ItemStack;
import tc.oc.pgm.action.Action;
import tc.oc.pgm.api.filter.Filter;
import tc.oc.pgm.api.player.MatchPlayer;

public class Icon implements Payable {

  private final ImmutableList<Payment> payments;
  private final ItemStack item;
  private final Filter filter;
  private final Action<? super MatchPlayer> action;
  private final boolean purchasable;
  private final Action<? super MatchPlayer> clickAction;

  public Icon(
      List<Payment> payments,
      ItemStack item,
      Filter filter,
      Action<? super MatchPlayer> action,
      boolean purchasable,
      Action<? super MatchPlayer> clickAction) {
    this.payments = ImmutableList.copyOf(payments);
    this.item = item;
    this.filter = filter;
    this.action = action;
    this.purchasable = purchasable;
    this.clickAction = clickAction;
  }

  @Override
  public List<Payment> getPayments() {
    return payments;
  }

  public ItemStack getItem() {
    return item;
  }

  public Filter getFilter() {
    return filter;
  }

  public Action<? super MatchPlayer> getAction() {
    return action;
  }

  public boolean isPurchasable() {
    return purchasable;
  }

  public Action<? super MatchPlayer> getClickAction() {
    return clickAction;
  }
}
